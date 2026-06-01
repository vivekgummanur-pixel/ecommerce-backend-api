package com.tiger.ecom_proj.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tiger.ecom_proj.model.Product;
import com.tiger.ecom_proj.repository.ProductRepo;

@Service
public class ProductService
{

	@Autowired
	ProductRepo pr;
	public List<Product> getAllProducts() 
	{
		
		return pr.findAll();
	}
	public Product getProductById(int id) 
	{
		
		return pr.findById(id).orElse(null);
	}
	public Product addProduct(Product product, MultipartFile imageFile) throws IOException 
	{
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageFile(imageFile.getBytes());
		
		return pr.save(product);
		
	}
	public Product getImageFile(int productId) 
	{
		
		return pr.findById(productId).orElse(null);
	}
	public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException
	{
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageFile(imageFile.getBytes());
		
		return pr.save(product);
	}
	public void deleteProduct(int id) 
	{
		pr.deleteById(id);	
	}
	public List<Product> searchProducts(String keyword) {
		
		return pr.searchProducts(keyword);
	}
	
	

}
