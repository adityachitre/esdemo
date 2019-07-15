package com.learn.esdemo.services.persons;

import com.learn.esdemo.entities.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Person save(Person person);

    Optional<Person> findOne(String id);

    List<Person> findAll();
}
