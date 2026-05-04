package org.example.ecommerce.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
//    * id (must not be empty).
    @NotEmpty(message = "id can not be empty")
    private String  id;
//
//            * productid (must not be empty).
    @NotEmpty(message = "productid can not be empty")
    private String productid;
//
//            * merchantid (must not be empty).
    @NotEmpty(message = "merchantid can not be empty")
    private String merchantid;
//
//            * stock (must not be empty, have to be more than 10 at start).
    @NotNull(message = "Stock can not be empty")
//    @Size(min=10,message = "Stock should be more then 10")
    private Integer stock;
}
