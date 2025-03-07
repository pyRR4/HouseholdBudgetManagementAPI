package com.example.transactions.service.generic;

public interface CRUDService<T, ID> extends ReadOnlyService<T, ID>, CreateDeleteService<T, ID> {
    T update(ID id, T dto);
}
