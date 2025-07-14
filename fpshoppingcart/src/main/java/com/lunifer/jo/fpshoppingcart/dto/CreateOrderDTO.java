package com.lunifer.jo.fpshoppingcart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderDTO {
    @NotBlank(message = "Shipping address is required")
    @Size(max = 255, message = "Shipping address cannot exceed 255 characters")
    private String shippingAddress;

    // Optional: list of items if creating order directly (if not, backend should use cart)
    private List<OrderItemRequestDTO> items;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;
}