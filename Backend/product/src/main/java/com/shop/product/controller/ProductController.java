package com.shop.product.controller;

import com.shop.product.dto.ProductDTO;
import com.shop.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getallproducts")
    public List<ProductDTO> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/product/{productid}")
    public ProductDTO getProductById(@PathVariable Integer productid){
        return productService.getProductById(productid);
    }

    @PostMapping("/addproduct")
    public ProductDTO saveUser(@RequestBody ProductDTO productDTO){
        return productService.saveproduct(productDTO);
    }
    @PutMapping("/updateproduct")
    public ProductDTO updateuser(@RequestBody ProductDTO productDTo){
        return productService.updateproduct(productDTo);
    }
    @DeleteMapping("/deleteproduct")
    public String deleteProduct(@RequestBody ProductDTO productDTo){
        return productService.deleteProduct(productDTo);
    }
    @DeleteMapping("deleteproduct/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        return productService.deleteUser_Id(id);
    }

}
