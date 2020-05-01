package com.ps.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ps.model.Product;
import com.ps.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	// @PreAuthorize("hasRole('ADMIN')")
	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}

	public Product addProduct(Product product) {
		return productRepo.save(product);
	}

	public Product updateProduct(String productId, Product product) {
		Optional<Product> findById = productRepo.findById(productId);
		if (findById.isPresent()) {

			if (product.getProductName() != null)
				findById.get().setProductName(product.getProductName());

			if (product.getPrice() != 0)
				findById.get().setPrice(product.getPrice());

			productRepo.save(findById.get());

			return findById.get();
		}

		throw new RuntimeException("Product Id not found !!!");
	}

	public void removeProduct(String productId) {
		Optional<Product> findById = productRepo.findById(productId);

		if (findById.isPresent()) {
			productRepo.delete(findById.get());
		} else {
			throw new RuntimeException("Product Id not found !!!");
		}

	}

	public Product getProductBuId(String productId) {
		Optional<Product> findById = productRepo.findById(productId);

		if (findById.isPresent()) {
			return findById.get();
		}

		throw new RuntimeException("Product Id not found !!!");
	}
}
