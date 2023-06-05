package ru.youlola.spring.demoSite.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.youlola.spring.demoSite.dto.BucketDTO;
import ru.youlola.spring.demoSite.dto.BucketDetailDTO;
import ru.youlola.spring.demoSite.models.Bucket;
import ru.youlola.spring.demoSite.models.Person;
import ru.youlola.spring.demoSite.models.Product;
import ru.youlola.spring.demoSite.repositories.BucketRepository;
import ru.youlola.spring.demoSite.repositories.PeopleRepository;
import ru.youlola.spring.demoSite.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class BucketService {

    private BucketRepository bucketRepository;
    private ProductRepository productRepository;
    private PeopleRepository peopleRepository;


    public BucketService(BucketRepository bucketRepository, ProductRepository productRepository,
                         PeopleRepository peopleRepository) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.peopleRepository = peopleRepository;
    }

    @Transactional
    public Bucket createBucket(Person person, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setPerson(person);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProduct(productList);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    public void addProduct(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProduct();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProduct(newProductList);
        bucketRepository.save(bucket);
    }

    public void deleteProduct(Long bucketId, Long productId) {
        Bucket bucket = bucketRepository.getById(bucketId);
        List<Product> products = bucket.getProduct().stream().filter(product -> product.getId() != productId).collect(Collectors.toList());
        bucket.setProduct(products);
        bucketRepository.save(bucket);
    }

    public BucketDTO getBucketByPerson(String username) {
        Person person = peopleRepository.findByUsername(username);
        Bucket bucket = person.getBucket();
        List<BucketDetailDTO> products = new ArrayList<>();
        List<Product> bucketProduct = bucket.getProduct();
        int sum = 0;
        for (Product product : bucketProduct) {
            products.add(new BucketDetailDTO(product, bucket.getId()));
            sum += product.getPrice().intValue();
        }
        return
        BucketDTO.builder()
                .id(bucket.getId()).bucketDetails(products).amountProducts(products.size()).sum(Double.valueOf(sum))
                .build();
    }

}
