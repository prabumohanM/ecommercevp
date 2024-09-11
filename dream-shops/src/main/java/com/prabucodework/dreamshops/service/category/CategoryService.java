package com.prabucodework.dreamshops.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.prabucodework.dreamshops.exceptions.AlreadyExistsException;
import com.prabucodework.dreamshops.exceptions.ResourceNotFoundException;
import com.prabucodework.dreamshops.model.Category;
import com.prabucodework.dreamshops.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
	private final CategoryRepository categoryRepository;
	
	 @Autowired
	    public CategoryService(CategoryRepository categoryRepository) {
	        this.categoryRepository = categoryRepository;
	    }
	
	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
	}

	@Override
	public Category getCategoryByName(String name) {
		return  categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
 
	@Override
	public Category addCategory(Category category) {
				return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
						.map(categoryRepository :: save)
						.orElseThrow(() -> new AlreadyExistsException(category.getName()+" already exists"));
						}

	@Override
	public Category updateCategory(Category category , Long id) {
		return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
			oldCategory.setName(category.getName());
			return categoryRepository.save(oldCategory);
		}) .orElseThrow(() -> new ResourceNotFoundException("category not found!"));
	}

	@Override
	public void deleteCategoryById(Long id) {
		categoryRepository.findById(id)
		.ifPresentOrElse(categoryRepository :: delete, () -> {
			throw new ResourceNotFoundException("Category not found!");
		});
	}
	

}
