package com.ecommerce.project.payload.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageableDTO {

    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String sortOrder;
}
