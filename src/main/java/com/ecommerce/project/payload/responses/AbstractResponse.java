package com.ecommerce.project.payload.responses;

import java.util.List;

import org.springframework.data.domain.Page;

public abstract class AbstractResponse<R, E, D> {

    public abstract R generate(Page<E> page, List<D> dtos);
}
