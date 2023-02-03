package com.falcao.cordstore.repositories;

import com.falcao.cordstore.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @SuppressWarnings("SpringDataMongoDBJsonFieldInspection")
    @Query(value = "{'productName': {$regex: ?0, $options: 'i'}, 'visibility': true}")
    public List<Product> findByProductName(String name);

    @Override
    List<Product> findAll();

    @Query("{'visibility': true}")
    List<Product> findAllByVisibility();

    @SuppressWarnings("SpringDataMongoDBJsonFieldInspection")
    @Query(value = "{'brand': {$regex: ?0, $options: 'i'}, 'visibility': true}")
    List<Product> findByBrand(String brand);

    @Query("{'category': ?0, 'visibility' : true}")
    List<Product> findByCategory(String category);
}
