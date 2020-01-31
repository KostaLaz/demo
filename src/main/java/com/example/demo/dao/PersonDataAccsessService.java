package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("personDao")
public class PersonDataAccsessService implements PersonDao {

    private static List<Person> db = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {

        db.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return db;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return db.stream()
                .filter(person -> person.getId()
                        .equals(id)).findFirst();

    }

    @Override
    public int deletePersonById(UUID id) {
       Optional<Person> personMaybe =  selectPersonById(id);
        if(personMaybe.isEmpty()){
            return 0;
        }
        db.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person updatePerson) {
        return selectPersonById(id)
                .map(person -> {int indexOfPersonToUpdate = db.indexOf(person);
                  if (indexOfPersonToUpdate >= 0){
                      db.set(indexOfPersonToUpdate, new Person(id, updatePerson.getName()));
                      return 1;
                  }
                  return 0;
                }).orElse(0);


    }

}
