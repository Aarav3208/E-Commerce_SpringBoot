package com.tyrant.SpringEcom.service;

import com.tyrant.SpringEcom.model.product;
import com.tyrant.SpringEcom.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    public List<product> getAllProducts () {
    return productRepo.findAll();
    }

    public product getProductById (int id) {
        return productRepo.findById(id).orElse(null);
    }

    public product addorUpdateProduct (product product, MultipartFile image) throws IOException {
        product.setImageName (image.getOriginalFilename ());
        product.setImageType (image.getContentType ());
        product.setImageData (image.getBytes ());
        return productRepo.save(product);
    }

    public void deleteProduct (int id) {
        productRepo.deleteById(id);
    }

    public List<product> searchProducts (String keyword) {
        return productRepo.searchProducts(keyword);
    }
}
