package com.prabucodework.dreamshops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prabucodework.dreamshops.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
		Category  findByName(String name);
		boolean existsByName(String name);
}
