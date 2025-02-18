package com.example.transactions.service.generic;

import java.util.List;

public interface CRUDService<T, ID> {
    T create(T dto);
    T update(ID id, T dto);
    void delete(ID id);
    T getById(ID id);
    List<T> getAll();
}
