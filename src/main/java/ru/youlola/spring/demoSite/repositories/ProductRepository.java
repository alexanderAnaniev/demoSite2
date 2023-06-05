package ru.youlola.spring.demoSite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.youlola.spring.demoSite.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

}
