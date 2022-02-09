package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RequestMapping("api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void addPerson(@Valid @NotNull @RequestBody Person person) {
        personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") long id) {
        return personService.getPersonById(id).orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public void deletePersonById(@PathVariable("id") long id){
        personService.deletePerson(id);
    }

    @PutMapping(path = "{id}")
    public void updatePerson(@PathVariable("id") long id, @Valid @NotNull @RequestBody Person personToUpdate){
        personService.updatePerson(id, personToUpdate);
    }

    @GetMapping(path = "/names-by-char/{letter}")
    public String getNamesByCharacter(@PathVariable ("letter") String letter){
        return personService.getNamesByCharacter(letter);
    }

    @GetMapping(path = "/names-by-char2/{letter}")
    public String getNamesByCharacter2(@PathVariable ("letter") String letter){
        return personService.getNamesByCharacter2(letter);
    }

    @GetMapping(path = "/work")
    public String getWorkByPerson(@RequestParam ("name") String name, @RequestParam ("surname") String surname){
        return personService.getWorkByPerson(name, surname);
    }



}

















