package org.example.ecommerce.Controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.ecommerce.ApiResponse.ApiResponse;
import org.example.ecommerce.Model.Product;
import org.example.ecommerce.Model.User;
import org.example.ecommerce.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(userService.addUser(user)) {
            return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User already exists"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if(userService.updateUser(id, user)) {
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        if(userService.deleteUser(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }

    @PutMapping("/buy-product/{userid}/{productid}/{merchantid}")
    public ResponseEntity<?> buyProduct(@PathVariable String userid,@PathVariable String productid,@PathVariable String merchantid ) {
        switch(userService.buyproduct(userid,productid,merchantid)){
            case 0:
                    return ResponseEntity.status(400).body(new ApiResponse("product not found"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("merchant not found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("User not found"));
            case 3:
                return ResponseEntity.status(400).body(new ApiResponse("merchant Stock not found"));
            case 4:
                return ResponseEntity.status(400).body(new ApiResponse("0 stock for this product"));
            case 5:
                return ResponseEntity.status(400).body(new ApiResponse("Tour balance is less than product price"));
            default:
                return ResponseEntity.status(200).body(new ApiResponse("product bought successfully"));
        }

    }

    @PutMapping("/add-balance/{id}/{balance}")
    public ResponseEntity<?> addBalance(@PathVariable String id,@PathVariable double balance) {
        if(userService.addBalance(id,balance)) {
            return ResponseEntity.status(200).body(new ApiResponse("Balance added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }

    @GetMapping("/get-admin-users")
    public ResponseEntity<?> getAdminUsers() {
        if(userService.getAdminUsers().isEmpty()) {
            return ResponseEntity.status(200).body(new ApiResponse("Admin users not found"));
        }
        return ResponseEntity.status(200).body(userService.getAdminUsers());
    }

    @PutMapping("/add-product-to-cart/{userid}/{productid}/{merchantid}")
    public ResponseEntity<?> addProductToCart(@PathVariable String userid,@PathVariable String productid,@PathVariable String merchantid) {
        switch (userService.addProductToCart(userid,productid,merchantid)){
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("product not found"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("No Merchant found"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("User not found"));
            case 3,4:
                return ResponseEntity.status(400).body(new ApiResponse("No stock for this product"));
            default:
                return ResponseEntity.status(200).body(new ApiResponse("product added to the cat"));
        }
    }

    @PutMapping("/delete-product-from-cart/{userid}/{productid}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable String userid,@PathVariable String productid) {
        if(userService.deleteProductFromCart(userid,productid)) {
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User not found"));
    }

    @GetMapping("/get-user-cart/{userid}")
    public ResponseEntity<?> getUserCart(@PathVariable String userid) {
        if(userService.getUserCart(userid)==null) {
            return ResponseEntity.status(200).body(new ApiResponse("User not found"));
        }
        if(userService.getUserCart(userid).isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("User cart is empty"));
        }
        return ResponseEntity.status(200).body(userService.getUserCart(userid));
    }

    @PutMapping("/price-discount/{userid}/{discount}")
    public ResponseEntity<?> priceDiscount(@PathVariable String userid,@PathVariable int discount){
        if(discount<0) {
            return ResponseEntity.status(400).body(new ApiResponse("discount must be positive"));
        }
        switch(userService.applyDiscount(userid,discount)){
            case 0:
                return ResponseEntity.status(400).body(new ApiResponse("User not found"));
            case 1:
                return ResponseEntity.status(400).body(new ApiResponse("You dont have permission to apply discount"));
            case 2:
                return ResponseEntity.status(400).body(new ApiResponse("no products found"));
            default:
                return ResponseEntity.status(200).body(new ApiResponse("discount applied successfully"));
        }


    }


}
