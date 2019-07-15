package com.learn.esdemo.transformers;

import com.learn.esdemo.dto.PersonDTO;
import com.learn.esdemo.entities.Person;

public final class PersonTransformer {

    private PersonTransformer() {

        // No instances allowed
    }

    public static Person transform(PersonDTO personDTO) {

        Person person = new Person();
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        return person;
    }
}
