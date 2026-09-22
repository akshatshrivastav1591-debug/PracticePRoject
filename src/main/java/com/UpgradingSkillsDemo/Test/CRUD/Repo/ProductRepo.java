package com.UpgradingSkillsDemo.Test.CRUD.Repo;

import com.UpgradingSkillsDemo.Test.CRUD.Pojo.ProductPojo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<ProductPojo,Integer> {
}
