package org.example.ecommerce.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "Id Can not be empty")
    private String  id;
    @NotEmpty(message = "Id Can not be empty")
    @Size(min=4,message = "Name must be more then 3 character")
    private String  name;
}
