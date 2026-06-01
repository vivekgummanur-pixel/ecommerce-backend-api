package com.tiger.ecom_proj.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tiger.ecom_proj.model.Product;
import com.tiger.ecom_proj.service.ProductService;

@RestController
@CrossOrigin(origins = "*") //or @CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ProductController 
{
	@Autowired
	ProductService ps;
	
	@RequestMapping("/")
	public String greet()
	{
		return "Hello World";
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts()
	{
		return new ResponseEntity(ps.getAllProducts(),HttpStatus.OK);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable int id)
	{
		Product product=ps.getProductById(id);
		
		if(product!=null)
		return new ResponseEntity<Product>(product,HttpStatus.OK);
		else 
			
		return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
			
	}
	
	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile)
	{
		try
		{
		    Product p1=ps.addProduct(product,imageFile);
		    return new ResponseEntity<>(p1,HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/product/{productId}/image")
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId)
	{
		Product p= ps.getImageFile(productId);
		byte[] imageFile=p.getImageFile();
		
		return ResponseEntity.ok().contentType(MediaType.valueOf(p.getImageType())).body(imageFile);
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product,
			@RequestPart MultipartFile imageFile)
	{
		
		Product p=null;
		try 
		{
			p=ps.updateProduct(id,product,imageFile);
		} 
		catch (IOException e) 
		{
			return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
		}
		
		if(p!=null) 
			return new ResponseEntity<>("Updated ",HttpStatus.OK);
		else
			return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id)
	{
		Product p=ps.getProductById(id);
		
		if(p!=null)
		{
			ps.deleteProduct(id);
			return new ResponseEntity<>("Deleted",HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("Deleted",HttpStatus.NOT_FOUND);	
	}
	
	@GetMapping("/product/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword)
	{
		List<Product> products=ps.searchProducts(keyword);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
	
}
