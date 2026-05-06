package org.example.ecommerce.Model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "id can not be empty")
    private String  id;

    @NotEmpty(message = "productid can not be empty")
    private String productid;

    @NotEmpty(message = "merchantid can not be empty")
    private String merchantid;

    @NotNull(message = "Stock can not be empty")
    @Min(value = 10,message = "Stock should be more then 10")
    private Integer stock;
}
