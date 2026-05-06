package org.example.ecommerce.Controller;


import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.ApiResponse.ApiResponse;
import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Services.MerchantService;
import org.example.ecommerce.Services.MerchantStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchantstock")
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ArrayList<MerchantStock> getAllMerchants() {
        return merchantStockService.getMerchantStock();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(merchantStock.getStock()<10)
            return ResponseEntity.status(400).body(new ApiResponse("Merchant stock must be more than 10"));

        switch (merchantStockService.addMerchantStock(merchantStock)) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("No product with this id"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant with this id"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("merchantStock  with this id exists"));
            default:
                return ResponseEntity.status(200).body(new ApiResponse("Merchant stock added successfully"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>  updateMerchant(@PathVariable String id,@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(merchantStock.getStock()<10)
            return ResponseEntity.status(400).body(new ApiResponse("merchantStock stock must be more than 10"));

        switch (merchantStockService.updateMerchantStock(id,merchantStock)) {
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("No product with this id"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("No merchant with this id"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("No merchantStock with this id"));
            default:
                return ResponseEntity.status(200).body(new ApiResponse("Merchant stock updated successfully"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
        if(merchantStockService.deleteMerchantStock(id))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }

    @PutMapping("/add-stock/{productid}/{merchantid}/{stock}")
    public ResponseEntity<?> addStock(@PathVariable String productid,@PathVariable String merchantid,@PathVariable Integer stock) {
        switch (merchantStockService.addStock(productid,merchantid,stock)){
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("product not exists"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant not exists"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant stock added successfully"));
        }
    }

    //extra
    @GetMapping("/available-product")
    public ResponseEntity<?> getAvailableProducts(){
        if(merchantStockService.getAvailabeProducts().isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No available products"));
        return ResponseEntity.status(200).body(merchantStockService.getAvailabeProducts());
    }

    @GetMapping("/get-product-by-merchant/{id}")
    public ResponseEntity<?> getProductByMerchant(@PathVariable String id){
        if(merchantStockService.getProductBymetchant(id)==null || merchantStockService.getProductBymetchant(id).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No product for this Merchant"));
        return ResponseEntity.status(200).body(merchantStockService.getProductBymetchant(id));
    }
    @GetMapping("/get-Product-merchant/{id}")
    public ResponseEntity<?> getProductMerchant(@PathVariable String id){
        if(merchantStockService.getProductMerchant(id).isEmpty())
            return ResponseEntity.status(400).body(new ApiResponse("No merchant provide this product"));
        return ResponseEntity.status(200).body(merchantStockService.getProductMerchant(id));
    }
}
