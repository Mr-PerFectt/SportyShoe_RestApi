package com.mrperfect.shoestore.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrperfect.shoestore.model.Category;



public interface CategoryRepo  extends JpaRepository<Category, Long> {

}
