package com.UpgradingSkillsDemo.Test.Security.Jwt;
import com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.CustomException.AccessTokenExceptions;
import com.UpgradingSkillsDemo.Test.Security.Pojo.UserSecurityPojoClass;
import com.UpgradingSkillsDemo.Test.Security.UserPrinciple.UserPrinciple;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
public class JwtFilterClass extends OncePerRequestFilter {

    @Autowired
    private JwtFilterServiceClass jwt;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/chat"); // ← skip JWT for websocket

    }


    private String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // strip "Bearer " prefix
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = extractTokenFromHeader(request);
            String userEmail = null;
            String userRole = null;
            System.out.println("Extracted Token:" + token);
            if (token != null) {
                userEmail = jwt.extractEmail(token);
                userRole = jwt.extractRole(token);
            }
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userdetails = getUserDetailsForJwtValidation(userEmail, userRole);
                if (jwt.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (AccessTokenExceptions e) {
            response.setStatus(e.getStatusCode());
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"message\":\"" + e.getMessage() + "\"}"
            );
            return;
        }
        filterChain.doFilter(request, response);
    }


    private UserDetails getUserDetailsForJwtValidation(String userEmail, String userRole) {
        UserSecurityPojoClass securityPojoClass = new UserSecurityPojoClass();
        securityPojoClass.setEmail(userEmail);
        securityPojoClass.setRole(userRole);
        return new UserPrinciple(securityPojoClass);
    }
}