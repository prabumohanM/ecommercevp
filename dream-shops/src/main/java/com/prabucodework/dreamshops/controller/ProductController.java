package com.prabucodework.dreamshops.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prabucodework.dreamshops.exceptions.AlreadyExistsException;
import com.prabucodework.dreamshops.exceptions.ResourceNotFoundException;
import com.prabucodework.dreamshops.model.Category;
import com.prabucodework.dreamshops.model.Product;
import com.prabucodework.dreamshops.request.AddProductRequest;
import com.prabucodework.dreamshops.request.ProductUpdateRequest;
import com.prabucodework.dreamshops.response.ApiResponse;
import com.prabucodework.dreamshops.service.category.CategoryService;
import com.prabucodework.dreamshops.service.product.IProductService;
import com.prabucodework.dreamshops.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

	private final IProductService productService;
	public ProductController(ProductService productService) {
        this.productService = productService;
    }
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts(){
		try {
			List<Product> products = productService.getAllProducts();
			return ResponseEntity.ok(new ApiResponse("Success!", products));
		}	catch (Exception e)	{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ApiResponse( "Error", e.getMessage()));
		}
	}
	
	@GetMapping("/product/{productId}/product")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
		try {

            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Success!", product));
		}
		catch(ResourceNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add product Success", theProduct));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
	
	@PutMapping("/product/{productId}/update")
	public ResponseEntity<ApiResponse> updateProduct( @RequestBody ProductUpdateRequest request,@PathVariable Long productId){
		try {

             Product theProduct = productService.updateProduct(request,productId);
            return ResponseEntity.ok(new ApiResponse("Update Success!", null));
		}
		catch(ResourceNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/product/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
		try {

             productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success!", null));
		}
		catch(ResourceNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	 @GetMapping("products/by/brand-and-name")
	    public ResponseEntity<ApiResponse> getProductByBrandAndName(
	        @RequestParam String brandName, @RequestParam String productName) {
	        try {
	            List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
	            if (products.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new ApiResponse("No Products Found", null));
	            }
	            return ResponseEntity.ok(new ApiResponse("Success", products));
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse(e.getMessage(), null));
	        }
	    }
	 
	 @GetMapping("products/by/category-and-brand")
	    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(
	        @RequestParam String category, @RequestParam String brand) {
	        try {
	            List<Product> products = productService.getProductByCategoryAndBrand(category, brand);
	            if (products.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new ApiResponse("No Products Found", null));
	            }
	            return ResponseEntity.ok(new ApiResponse("Success", products));
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse(e.getMessage(), null));
	        }
	    }
	
	 @GetMapping("/product/{name}/products")
		public ResponseEntity<ApiResponse> getProductByName(@RequestParam String name){
			try {
	            List<Product> products = productService.getProductsByName(name);
	            if (products.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new ApiResponse("No Products Found", null));
	            }
	            return ResponseEntity.ok(new ApiResponse("Success!", products));
			}
			catch(ResourceNotFoundException e) {

	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse(e.getMessage(), null));
			}
		}
	 
	 @GetMapping("product/by-brand")
	    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
	        try {
	            List<Product> products = productService.getProductByBrand(brand);
	            if (products.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new ApiResponse("No Products Found", null));
	            }
	            return ResponseEntity.ok(new ApiResponse("Success", products));
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse(e.getMessage(), null));
	        }
	    }
	 
	 @GetMapping("product/{category}/all/products")
	    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String category) {
	        try {
	            List<Product> products = productService.getProductsByCategory(category);
	            if (products.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new ApiResponse("No Products Found", null));
	            }
	            return ResponseEntity.ok(new ApiResponse("Product Found", products));
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse(e.getMessage(), null));
	        }
	 }
	 
	 @GetMapping("product/count/by-brand/and-name")
	    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand,@RequestParam String name) {
	        try {
	            var productCount = productService.countProductsByBrandAndName(brand,name);
	            return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
	            } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new ApiResponse(e.getMessage(), null));
	    }
}
}



