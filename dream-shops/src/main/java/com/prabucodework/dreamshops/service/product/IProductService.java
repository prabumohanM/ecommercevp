package com.prabucodework.dreamshops.service.product;

import java.util.List;

import com.prabucodework.dreamshops.model.Product;
import com.prabucodework.dreamshops.request.AddProductRequest;
import com.prabucodework.dreamshops.request.ProductUpdateRequest;

public interface IProductService {
	Product addProduct(AddProductRequest product);
	Product getProductById(Long id);
	void deleteProductById(Long id);
	Product updateProduct(ProductUpdateRequest request, Long productId);
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductByBrand(String brand);
	List<Product> getProductByCategoryAndBrand(String category, String brand);
    
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String category, String name);
	Long countProductsByBrandAndName(String brand, String name);
	
	

}
