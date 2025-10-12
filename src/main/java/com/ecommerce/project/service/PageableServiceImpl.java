package com.ecommerce.project.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.project.payload.dtos.PageableDTO;

@Service
public class PageableServiceImpl implements PageableService {

    @Override
    public Pageable getPageDetails(PageableDTO pageableDTO) {
        Sort sortByAndOrder = pageableDTO.getSortOrder().equalsIgnoreCase("asc")
                ? Sort.by(pageableDTO.getSortBy()).ascending()
                : Sort.by(pageableDTO.getSortBy()).descending();

        return PageRequest.of(pageableDTO.getPageNumber(), pageableDTO.getPageSize(), sortByAndOrder);
    }
}
