package ru.youlola.spring.demoSite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.youlola.spring.demoSite.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Long> {
    Person findByUsername(String username);
}
