package com.beck.dao;

import com.beck.model.Product;

import java.util.List;
import java.util.Optional;

public interface Dao<T,E> {

    E save(E entity);

    void update(E entity);

    Optional<E> findById(T id);

    void deleteById(T id);

    List<E> findAll();


}
