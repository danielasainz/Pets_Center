package com.sainz.demo;

import org.springframework.data.repository.CrudRepository;

public interface petRepository extends CrudRepository<Pet,Long> {

}
