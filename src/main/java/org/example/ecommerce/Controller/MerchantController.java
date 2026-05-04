package org.example.ecommerce.Controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.ApiResponse.ApiResponse;
import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Services.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ArrayList<Merchant> getAllMerchants() {
        return merchantService.getMerchants();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(merchantService.addMerchant(merchant))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("Merchant already exists"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>  updateMerchant(@PathVariable String id,@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(merchantService.updateMerchant(id, merchant))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id) {
        if(merchantService.deleteMerchant(id))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully"));
        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
    }




}
