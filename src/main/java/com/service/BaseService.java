package com.service;

import com.model.BaseEntity;
import com.repository.BaseRepository;

import java.io.Serializable;
import java.util.List;

public abstract class BaseService<R extends BaseRepository<E, ID>, E, ID extends Serializable> {

    public abstract R getRepository();

    public List<E> findAll() {
        return getRepository().findAll();
    }

    public E findOne(ID id){
        return getRepository().findOne(id);
    }

    public long countAll() {
        return getRepository().countAll();
    }

    public E create(E entity) {
        return getRepository().insert(entity);
    }

    public E update(E entity) {
        return getRepository().update(entity);
    }

    public List<E> create(List<E> entities) {
        getRepository().insert(entities);
        return entities;
    }

    public List<E> update(List<E> entities) {
        getRepository().update(entities);
        return entities;
    }

    public long delete(ID id) {
        return getRepository().delete(id);
    }

}