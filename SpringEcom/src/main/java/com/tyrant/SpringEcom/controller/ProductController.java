package com.tyrant.SpringEcom.controller;

import com.tyrant.SpringEcom.model.product;
import com.tyrant.SpringEcom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/products")
    public ResponseEntity<List<product>> getProducts () {
        return new ResponseEntity<> (productService.getAllProducts (), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<product> getProductById (@PathVariable int id) {
        product productA = productService.getProductById (id);
        if ( productA != null ) {
            return new ResponseEntity<> (productA, HttpStatus.OK);
        } else
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
    }
    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId (@PathVariable int productId) {
        product product=productService.getProductById(productId);
        return new ResponseEntity<> (product.getImageData (), HttpStatus.OK);
    }


    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart product product, @RequestPart MultipartFile imageFile){
        product savedProduct= null;
        try {
            savedProduct = productService.addorUpdateProduct (product,imageFile);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
        } catch ( IOException e ) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
    @PutMapping("/product/{id}")
        public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart product product, @RequestPart MultipartFile imageFile) {
            product updatedProduct=null;
            try {
                updatedProduct=productService.addorUpdateProduct (product,imageFile);
                return new ResponseEntity<>("Updated",HttpStatus.OK);
            } catch (IOException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    }
    @DeleteMapping("/product/{id}")
        public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        product product=productService.getProductById(id);
        if(product!=null) {
            productService.deleteProduct (id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/products/search")
    public ResponseEntity<List<product>> searchProducts(@RequestParam String keyword){
        List<product> products=productService.searchProducts(keyword);
        System.out.println (keyword);
        return new ResponseEntity<> (products , HttpStatus.OK);
    }
}
