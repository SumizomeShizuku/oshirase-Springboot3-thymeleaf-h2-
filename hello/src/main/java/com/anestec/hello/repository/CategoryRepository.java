package com.anestec.hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anestec.hello.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
