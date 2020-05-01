package com.ps.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.model.Product;
import com.ps.model.ProductVo;
import com.ps.model.Response;
import com.ps.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/product")
@CrossOrigin
@Api(tags = "Allianz Product Service", description = "Product Management")
public class ProductController {

  @Autowired
  private ProductService service;

  @PostMapping("/add")
  @ApiOperation("Add a Product")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Product added successfully")})
  public ResponseEntity<Product> addProduct(
      @RequestHeader("Authorization") String authorizationToken,
      @RequestBody ProductVo productData) {
    Product product = new ObjectMapper().convertValue(productData, Product.class);
    Product addProduct = service.addProduct(product);
    return ResponseEntity.ok(addProduct);
  }

  @PutMapping("/update/{productId}")
  @ApiOperation("Update a Product")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Product Updated successfully"),
      @ApiResponse(code = 400, message = "Product Id Not Found")})
  public ResponseEntity<?> updateProduct(@RequestHeader("Authorization") String authorizationToken,
      @PathVariable String productId, @RequestBody ProductVo productData) {

    Product product = new ObjectMapper().convertValue(productData, Product.class);
    product.setProductId(productId);
    try {
      return ResponseEntity.ok(service.updateProduct(product.getProductId(), product));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/delete/{productId}")
  @ApiOperation("Delete a Product")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Product deleted successfully"),
      @ApiResponse(code = 400, message = "Product Id Not Found")})
  public ResponseEntity<?> deleteProduct(@RequestHeader("Authorization") String authorizationToken,
      @PathVariable String productId) {

    try {
      service.removeProduct(productId);
      Response resp = new Response();
      resp.setStatus("Product deleted successfully");
      return ResponseEntity.ok(resp);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation("Get All Product List")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Product List retrived Successfully")})
  public ResponseEntity<List<Product>> getAllProduct(
      @RequestHeader("Authorization") String authorizationToken) {
    return ResponseEntity.ok(service.getAllProduct());
  }

  @GetMapping("/get/{productId}")
  @ApiOperation("get a Product")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "get a Product"),
      @ApiResponse(code = 400, message = "Product Id Not Found")})
  public ResponseEntity<?> getProduct(@RequestHeader("Authorization") String authorizationToken,
      @PathVariable String productId) {
    try {
      return ResponseEntity.ok(service.getProductBuId(productId));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
