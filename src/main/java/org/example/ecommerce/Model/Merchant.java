package org.example.ecommerce.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "name can not be empty")
    private String id;
    @NotEmpty(message = "name can not be empty")
    @Size(min=4,message = "Merchant name must be more then 3")
    private String name;
}
