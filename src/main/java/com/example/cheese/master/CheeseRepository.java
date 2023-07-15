package com.example.cheese.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CheeseRepository extends CrudRepository<Cheese, Long>, PagingAndSortingRepository<Cheese, Long> {

}
