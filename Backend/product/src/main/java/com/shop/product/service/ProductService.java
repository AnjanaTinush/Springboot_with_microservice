package com.shop.product.service;

import com.shop.product.dto.ProductDTO;
import com.shop.product.model.Product;
import com.shop.product.repo.ProductRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional


public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts(){
        List<Product>userList =productRepo.findAll();
        return modelMapper.map(userList,new TypeToken<List<ProductDTO>>(){}.getType());
    }

    public ProductDTO getProductById(Integer productId){
        Product product = productRepo.getProductById(productId);
        return modelMapper.map(product,ProductDTO.class);
    }

    public ProductDTO saveproduct(ProductDTO productDTo){
        productRepo.save(modelMapper.map(productDTo,Product.class));
        return productDTo;
    }

    public ProductDTO updateproduct(ProductDTO productDTo){
        productRepo.save(modelMapper.map(productDTo,Product.class));
        return  productDTo;
    }
    public String deleteProduct(ProductDTO productDTo){
        productRepo.delete(modelMapper.map(productDTo,Product.class));
        return "Product deleted";
    }

    public String deleteUser_Id(Integer productId){
        productRepo.deleteById(productId);
        return "Product deleted";
    }


}
