package com.prabucodework.dreamshops.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prabucodework.dreamshops.dto.ImageDto;
import com.prabucodework.dreamshops.exceptions.ResourceNotFoundException;
import com.prabucodework.dreamshops.model.Image;
import com.prabucodework.dreamshops.model.Product;
import com.prabucodework.dreamshops.repository.ImageRepository;
import com.prabucodework.dreamshops.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
	private final ImageRepository imageRepository;
	private final IProductService productService;
	
	 @Autowired
	    public ImageService(ImageRepository imageRepository, IProductService productService) {
	        this.imageRepository = imageRepository;
	        this.productService = productService;
	    }

	
	@Override
	public Image getImageById(Long id) {
		return imageRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
	
	}

	@Override
	public void deleteImageById(Long id) {
		imageRepository.findById(id).ifPresentOrElse(imageRepository :: delete, () -> {
			throw new ResourceNotFoundException("No image found with id: " +id);
		});
	}

	@Override
	public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
		Product product = productService.getProductById(productId);
		List<ImageDto> savedImageDto = new ArrayList<>();
		for(MultipartFile file : files) {
			try {
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				image.setProduct(product);
				
				String buildDownloadUrl = "/api/v1/images/image/download/";
				String downloadUrl = buildDownloadUrl +image.getId();
				image.setDownloadUrl(downloadUrl);
				Image savedImage = imageRepository.save(image);
				
				savedImage.setDownloadUrl(buildDownloadUrl +savedImage.getId());
				imageRepository.save(savedImage);
				
				ImageDto imageDto = new ImageDto();
				imageDto.setImageId(savedImage.getId());
				imageDto.setImageName(savedImage.getFileName());
				imageDto.setDownloadUrl(savedImage.getDownloadUrl());
				savedImageDto.add(imageDto);
				
				
			
			}	
			catch(IOException | SQLException e) {
				throw new RuntimeException("Error occurred while saving image", e);
			}
		}
		return savedImageDto;
	}

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		Image image = getImageById(imageId);
		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileName(file.getOriginalFilename());   
			image.setImage(new SerialBlob(file.getBytes()));	
			imageRepository.save(image);
		}	catch (IOException | SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
		
}
