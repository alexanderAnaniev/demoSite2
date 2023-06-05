package ru.youlola.spring.demoSite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.youlola.spring.demoSite.models.Bucket;

@Repository
public interface BucketRepository extends JpaRepository<Bucket,Long> {

}
