package org.example.ecommerce.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

//    * id (must not be empty).
    @NotEmpty(message = "Id can not be empty")
    private String id;
//
//            * username (must not be empty, have to be more than 5 length long).
    @NotEmpty(message = "Name can not be empty")
    @Size(min=6,message="Name should be more than 5")
    private String name;
//
//            * password (must not be empty, have to be more than 6 length long, must have
//characters and digits).
    @NotEmpty(message = "Password can not be empty")
    @Size(min = 6, message = "Password should be more than 6")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$")
    private String password;
//
//            * email (must not be empty, must be valid email).
    @NotEmpty(message = "Email can not be empty")
    @Email
    private String email;
//
//            * role (must not be empty, have to be in ( “Admin”,”Customer”)).
    @NotEmpty(message = "Role can not be empty")
    @Pattern(regexp = "(?i)^(Admin|Customer)$")
    private String role;
    @NotNull(message = "Balance can not be empty")
    @PositiveOrZero(message = "Balance must be positive")
    private Double balance;

    private ArrayList<Product> cart=new ArrayList<>();

}
