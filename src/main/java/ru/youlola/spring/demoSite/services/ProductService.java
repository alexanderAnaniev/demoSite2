package ru.youlola.spring.demoSite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.youlola.spring.demoSite.models.Bucket;
import ru.youlola.spring.demoSite.models.Person;
import ru.youlola.spring.demoSite.models.Product;
import ru.youlola.spring.demoSite.repositories.PeopleRepository;
import ru.youlola.spring.demoSite.repositories.ProductRepository;

import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class ProductService {
    ProductRepository productRepository;
    BucketService bucketService;
    PeopleRepository peopleRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, PeopleRepository peopleRepository, BucketService bucketService) {
        this.productRepository = productRepository;
        this.peopleRepository = peopleRepository;
        this.bucketService = bucketService;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional
    public void addToUserBucket(Long productId, String username) {
        Person person = peopleRepository.findByUsername(username);
        if (person == null) {
            throw new RuntimeException("User not found - " + username);
        }

        Bucket bucket = person.getBucket();
        if (bucket == null) {
            Bucket newBucket = bucketService.createBucket(person, Collections.singletonList(productId));
            person.setBucket(newBucket);
            peopleRepository.save(person);
        } else {
            bucketService.addProduct(bucket, Collections.singletonList(productId));
        }
    }
}



