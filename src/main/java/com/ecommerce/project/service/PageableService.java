package com.ecommerce.project.service;

import org.springframework.data.domain.Pageable;

import com.ecommerce.project.payload.dtos.PageableDTO;

public interface PageableService {

    Pageable getPageDetails(PageableDTO pageableDTO);
}
