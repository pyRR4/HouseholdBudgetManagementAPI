package com.example.transactions.service.generic;

import java.util.List;

public interface ReadOnlyService<T, ID> {
    T getById(ID id);
    List<T> getAll();
}
