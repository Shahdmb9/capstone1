package org.example.ecommerce.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.ApiResponse.ApiResponse;
import org.example.ecommerce.Model.Product;
import org.example.ecommerce.Services.CategoryService;
import org.example.ecommerce.Services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/proudct")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get")
    public ArrayList<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors) {
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        if(product.getSalesCount()>0)
            return ResponseEntity.status(400).body(new ApiResponse("Product sales must be 0 on adding "));

        switch (productService.addProduct(product)) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("No category with this id exists"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("product already exists"));
            default:
                return ResponseEntity.status(200).body(new ApiResponse("product added"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id ,@RequestBody @Valid Product product, Errors errors) {
        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        switch (productService.updateProduct(id,product)) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("No category with this id exists"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("No product with this id exists"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("product updated"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        if(productService.deleteProduct(id))
            return ResponseEntity.status(200).body(new ApiResponse("product deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("No product with this id exists"));
    }

    @GetMapping("/get-product-by-id")
    public ResponseEntity<?> getProductById(@PathVariable String id){
        if(productService.productExists(id)!=null)
            return ResponseEntity.status(200).body(productService.productExists(id));
        return ResponseEntity.status(400).body(new ApiResponse("No product with this id exists"));
    }

    @GetMapping("/get-product-by-category/{category}")
    public ResponseEntity<?> getProductByCategory(@PathVariable String category){
        if(productService.getProductsByCategory(category)==null)
            return ResponseEntity.status(400).body(new ApiResponse("No category called "+category));
        if(productService.getProductsByCategory(category).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No product in this category"));
        return ResponseEntity.status(200).body(productService.getProductsByCategory(category));
    }

    @GetMapping("/get-product-by-name/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name){
        if(productService.getProductsByName(name).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No product with this name exists"));
        return ResponseEntity.status(200).body(productService.getProductsByName(name));
    }

    @GetMapping("/get-product-by-price/{min}/{max}")
    public ResponseEntity<?> getProductByPrice(@PathVariable Double min, @PathVariable Double max){
        if(min>max)
            return ResponseEntity.status(400).body(new ApiResponse("Maximum price must be greater than Minimum price"));

        if(productService.getProductsByPrice(min,max).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No product with this price exists"));
        return ResponseEntity.status(200).body(productService.getProductsByPrice(min,max));
    }

    @GetMapping("/sort-product-by-price-cheap-to-exp")
    public ResponseEntity<?> sortProductfromCheaptoExpensive(){
        if(productService.sortProductsFromCheapToExpensive().isEmpty()|| productService.sortProductsFromCheapToExpensive()==null)
            return ResponseEntity.status(400).body(new ApiResponse("No product to sort"));
        return ResponseEntity.status(200).body(productService.sortProductsFromCheapToExpensive());
    }

    @GetMapping("/sort-product-by-price-exp-to-cheap")
    public ResponseEntity<?> sortProductsFromExpensiveToCheap(){
        if(productService.sortProductsFromExpensiveToCheap().isEmpty() || productService.sortProductsFromCheapToExpensive()==null)
            return ResponseEntity.status(400).body(new ApiResponse("No product to sort"));
        return ResponseEntity.status(200).body(productService.sortProductsFromExpensiveToCheap());
    }

    @GetMapping("/get-most-sales")
    public ResponseEntity<?> getMostSales(){
        if(productService.mostThreeSales()!=null)
            return ResponseEntity.status(200).body(productService.mostThreeSales());
        return ResponseEntity.status(400).body(new ApiResponse("No product found"));
    }

    @GetMapping("/get-most-sales/{category}")
    public ResponseEntity<?> getMostSalesByCategory(@PathVariable String category){
        if(productService.getProductsByCategory(category)==null)
            return ResponseEntity.status(400).body(new ApiResponse("No category called "+category));
        if(!productService.mostThreeSalesInCategory(category).isEmpty())
            return ResponseEntity.status(200).body(productService.mostThreeSalesInCategory(category));
        return ResponseEntity.status(400).body(new ApiResponse("No product found"));
    }


}
