package com.lunifer.jo.fpshoppingcart.payload;

import com.lunifer.jo.fpshoppingcart.dto.ShoppingCartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartResponse {

    private List<ShoppingCartDTO> content;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;
}
