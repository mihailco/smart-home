package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class HomeRequest {
   @NotBlank(message = "name is required")
   private String name;
   private String address;
}
