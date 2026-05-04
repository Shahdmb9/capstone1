package org.example.ecommerce.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    //    * id (must not be empty).
    @NotEmpty(message = "Id can not be empty")
    private String id;
    @NotEmpty(message = "Name can not be empty")
    @Size(min=4,message = "Name should be more than 4")
    private String name;
    @NotNull
    @PositiveOrZero(message = "price can not be negative")
    private Double price;
    //
    @NotEmpty(message = "CategorylD can not be empty")
    private String categorylD;

    //new attribute
    private int salesCount;

}
