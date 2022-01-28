package com.example.demo.dao;

import com.example.demo.model.Person;
import org.apache.catalina.LifecycleState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {

    int insertPerson(UUID is, Person person);

    default int insertPerson(Person person){
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<Person> selectAllPeople();

    Optional<Person> selectPersonById(UUID id);

    int deletePerson(UUID id);

    int updatePersonById(UUID id, Person person);
}
