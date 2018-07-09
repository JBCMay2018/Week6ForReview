package com.nguyen.week6challenge.repository;

import com.nguyen.week6challenge.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
