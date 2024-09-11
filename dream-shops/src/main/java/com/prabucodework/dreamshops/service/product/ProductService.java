package com.prabucodework.dreamshops.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prabucodework.dreamshops.exceptions.ProductNotFoundException;
import com.prabucodework.dreamshops.exceptions.ResourceNotFoundException;
import com.prabucodework.dreamshops.model.Category;
import com.prabucodework.dreamshops.model.Product;
import com.prabucodework.dreamshops.repository.CategoryRepository;
import com.prabucodework.dreamshops.repository.ProductRepository;
import com.prabucodework.dreamshops.request.AddProductRequest;
import com.prabucodework.dreamshops.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	
	@Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
	
	@Override
	public Product addProduct(AddProductRequest request) {
		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
				.orElseGet(() -> {
					Category newCategory = new Category(request.getCategory().getName());
					return categoryRepository.save(newCategory);
				});
		request.setCategory(category);
		return productRepository.save(createProduct(request, category));
	}

	private Product createProduct(AddProductRequest request, Category category) {
		return new Product(
				request.getName(),
				request.getBrand(),
				request.getPrice(),
	            request.getInventory(),  // Pass inventory as an int, if it's not a string
	            request.getDescription(),
	            category
				);
	}
	
	@Override 
	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Product Not Found!"));
	}
	

	@Override
	public void deleteProductById(Long id) {
		productRepository.findById(id)
		.ifPresentOrElse(productRepository::delete,
				()-> {throw new ResourceNotFoundException("Product Not Found!");});
	}

	@Override
	public Product updateProduct(ProductUpdateRequest request, Long productId) {
		return productRepository.findById(productId)
				.map(existingProduct -> updateExistingProduct(existingProduct, request))
				.map(productRepository :: save)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found!"));
	}
	
	private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());
		
		Category category = categoryRepository.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		return existingProduct;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category,brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		return productRepository.findByBrandAndName(brand,name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand,name);
	}

}
