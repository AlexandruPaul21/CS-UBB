package com.example.simulation3.service;

import com.example.simulation3.domain.Person;
import com.example.simulation3.repository.PersonDbRepository;

import java.util.List;

public class PersonService {
    private PersonDbRepository personDbRepository;

    public PersonService(PersonDbRepository personDbRepository) {
        this.personDbRepository = personDbRepository;
    }

    public Person save(Person person) {
        return personDbRepository.save(person);
    }

    public List<String> findUsernames() {
        return personDbRepository.findUsernames();
    }

    public Person findByUserPass(String username) {
        return personDbRepository.findByUserAndPass(username);
    }
}
