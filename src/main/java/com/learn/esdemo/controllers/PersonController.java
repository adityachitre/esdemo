package com.learn.esdemo.controllers;

import com.learn.esdemo.dto.PersonDTO;
import com.learn.esdemo.entities.Person;
import com.learn.esdemo.services.persons.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.learn.esdemo.transformers.PersonTransformer.transform;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAll() {

        return ok(personService.findAll());
    }

    @PostMapping
    public ResponseEntity<Person> add(@RequestBody PersonDTO personDTO) {

        Person person = transform(personDTO);
        personService.save(person);
        return ok(person);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> get(@PathVariable String id) {

        Optional<Person> personOpt = personService.findOne(id);
        if (personOpt.isPresent()) {
            return ok(personOpt.get());
        }

        return notFound().build();
    }
}
