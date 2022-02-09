package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.model.Work;
import com.example.demo.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.demo.repository.PersonRepository;

import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private WorkRepository workRepository;

    public void addPerson(Person person) {
        personRepository.save(person);
    }

    public List<Person> getAllPeople() {
        Iterable<Person> personIterable = personRepository.findAll();
        List<Person> personList = new ArrayList<>();
        /*
         * personIterable.forEach(personList ::add); //metodo forEch()
         */

        for (Person person : personIterable) {   //prende uno ad uno gli elementi della collezione personIterable, assegna ciascuno di essi alla variabile person
            personList.add(person);
        }
        return personList;
    }


    public Optional<Person> getPersonById(long id) {
        if (personRepository.existsById(id)) {
            return personRepository.findById(id);
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public void deletePerson(long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


    public void updatePerson(long id, Person updatePerson) {
        /*
        1. prelevare dal db  la persona con id = id
        2. verificare se il risultato è diverso da null o 0
            3. se è == a null o 0 torna "Exception 404 not found" (si fa col "throw new exception, non col return"
            4. se esiste sovrascrivi nome e cognome con l'oggetto newPerson e chiama la Save
        */

        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            person.get().setSurname(updatePerson.getSurname());
            person.get().setName(updatePerson.getName());
            personRepository.save(person.get());
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

    /*
    Creare un nuovo endpoint GET "getNamesByCharacter". Il metodo prenderà in input una lettera dell'alfabeto e
    ritornerà 1 stringa lunga contenente tutti i nomi presenti nel database che iniziano per la lettera passata
    in input (es.: invochiamo getNamesByCharacter() con input "a", nel database sono presenti 15 record di cui 3
    col nome che inizia con lettera A, il metodo ritornerà una stringa come segue: "Ale, Adriano, Aldo"
     */

    public String getNamesByCharacter(String letter) {
        String personString = "";
        String result = "";
        List<Person> personList = personRepository.findByNameStartingWith(letter);
        if(personList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        for (int i = 0; i < personList.size(); i++) {
            personString = (personList.get(i).getName());
            result = result + " " + personString;
        }
        return result;
    }

    public String getNamesByCharacter2(String letter){
        Iterable<Person> personIterable = personRepository.findAll();
        Iterator<Person> iterator = personIterable.iterator();
        //String result ="";
        StringBuilder result = new StringBuilder();
        while(iterator.hasNext()){
            Person person = iterator.next();
            if(person.getName().toLowerCase().startsWith(letter) || person.getName().toUpperCase().startsWith(letter)) { // startWith() case sensitive
                //result = result + person.getName();
                result.append(person.getName());
                result.append(" ");
            }
        }

        return result.toString();
    }

    /*
    creare la tabella Lavori, e creare un endpoint GET che dato in input Nome e Cognome restituisce il lavoro della persona
     */

    /*
    public String getWorkByPerson(String name, String surname) {
        Iterable<Person> personIterable = personRepository.findAll();
        Iterator<Person> iterator = personIterable.iterator();
        long personID;
        String result = "";
        Optional<Work> personIDwork;

        while (iterator.hasNext()) {
            Person personDB = iterator.next();
            if (personDB.getName().equalsIgnoreCase(name) && personDB.getSurname().equalsIgnoreCase(surname)){
                personID = personDB.getId();
                personIDwork = workRepository.findById(personID);
                 if(personIDwork.isPresent())
                   result = personIDwork.get().getWorkName();
            }
        }
        return result;

    }*/

    public String getWorkByPerson(String name, String surname) {
        Iterable<Person> personIterable = personRepository.findAll();
        Iterator<Person> iterator = personIterable.iterator();
        long personID;
        String result = "";
        Optional<Work> personIDwork;

        while (iterator.hasNext()) {
            Person personDB = iterator.next();
            if (personDB.getName().equalsIgnoreCase(name) && personDB.getSurname().equalsIgnoreCase(surname)){
                personID = personDB.getId();
                personIDwork = workRepository.findByPerson_id(personID);
                if(personIDwork.isPresent())
                    result = personIDwork.get().getWorkName();
            }
        }
        return result;

    }

}
