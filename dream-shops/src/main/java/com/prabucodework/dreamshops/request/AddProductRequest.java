package com.prabucodework.dreamshops.request;

import java.math.BigDecimal;

import com.prabucodework.dreamshops.model.Category;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class AddProductRequest {
	private Long id;
	private String name;
	private String brand;		
	private BigDecimal price;
	private int inventory;
	private String description;
	private Category category;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	 public String getName() {
	        return name;
	    }

	    // Setter for name (if needed)
	    public void setName(String name) {
	        this.name = name;
	    }
		
	
}

