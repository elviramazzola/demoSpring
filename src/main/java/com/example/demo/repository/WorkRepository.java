package com.example.demo.repository;

import com.example.demo.model.Work;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WorkRepository extends CrudRepository <Work, Long> {
        Optional<Work> findByPerson_id(long person_id);
}
