package com.prabucodework.dreamshops.controller;

import java.security.PublicKey;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabucodework.dreamshops.exceptions.AlreadyExistsException;
import com.prabucodework.dreamshops.exceptions.ResourceNotFoundException;
import com.prabucodework.dreamshops.model.Category;
import com.prabucodework.dreamshops.model.Product;
import com.prabucodework.dreamshops.response.ApiResponse;
import com.prabucodework.dreamshops.service.category.CategoryService;
import com.prabucodework.dreamshops.service.category.ICategoryService;
import com.prabucodework.dreamshops.service.product.ProductService;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
	private final ICategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllCategories(){
		try {
			List<Category> categories = categoryService.getAllCategories();
			return ResponseEntity.ok(new ApiResponse("Found!", categories));
		}	catch (Exception e)	{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ApiResponse( "Error", e.getMessage()));
		}
	}
		
		@PostMapping("/add")
	    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
	        try {
	            Category theCategory = categoryService.addCategory(category);
	            return ResponseEntity.ok(new ApiResponse("Success", theCategory));
	        } catch (AlreadyExistsException e) {
	            return ResponseEntity.status(HttpStatus.CONFLICT)
	                    .body(new ApiResponse(e.getMessage(), null));
	        }
	    }
		
		@GetMapping("/category/{id}/category")
		public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
			try {

	            Category theCategory = categoryService.getCategoryById(id);
	            return ResponseEntity.ok(new ApiResponse("Found!", theCategory));
			}
			catch(ResourceNotFoundException e) {

	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse(e.getMessage(), null));
			}
		}
		
		@GetMapping("/category/{name}/category")
		public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
			try {

	            Category theCategory = categoryService.getCategoryByName(name);
	            return ResponseEntity.ok(new ApiResponse("Found!", theCategory));
			}
			catch(ResourceNotFoundException e) {

	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse(e.getMessage(), null));
			}
		}
		
		@DeleteMapping("/category/{id}/delete")
		public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
			try {

	             categoryService.deleteCategoryById(id);
	            return ResponseEntity.ok(new ApiResponse("Delete category success!", null));
			}
			catch(ResourceNotFoundException e) {

	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse(e.getMessage(), null));
			}
		}
		
		@PutMapping("/category/{id}/update")
		public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category){
			try {

	             Category updateCategory = categoryService.updateCategory(category,id);
	            return ResponseEntity.ok(new ApiResponse("Update Success!", null));
			}
			catch(ResourceNotFoundException e) {

	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse(e.getMessage(), null));
			}
		}
		
		
	}

