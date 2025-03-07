package com.example.transactions.service.generic;

public interface CreateDeleteService<T, ID> {
    T create(T dto);
    void delete(ID id);
}
