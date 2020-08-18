package com.repository;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class BaseRepository<T,ID extends Serializable> {

    @PersistenceContext
    private EntityManager entityManager;
    private final Class entityClass;

    public BaseRepository(Class entityClass) {
        this.entityClass = entityClass;
    }
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    //====== Build Query Coding ======

    private Query buildQuery(String query, boolean isNative, Class clazz) {
        Query createdQuery;
        if (isNative) {
            if (clazz != null) {
                createdQuery = entityManager.createNativeQuery(query, clazz);
            } else {
                createdQuery = entityManager.createNativeQuery(query);
            }
        } else {
            createdQuery = entityManager.createQuery(query);
        }
        return createdQuery;
    }

    private Query buildQuery(String query, boolean isNative) {
        return buildQuery(query, isNative, null);
    }

    public Query buildQueryHasParameters(String query, boolean isNative, Map<String, Object> params) {
        return buildQueryHasParameters(query, isNative, params, null);
    }

    public Query buildQueryHasParameters(String query, boolean isNative, Map<String, Object> params, Class clazz) {
        Query createdQuery;
        if (clazz != null) {
            createdQuery = buildQuery(query, isNative, clazz);
        } else {
            createdQuery = buildQuery(query, isNative);
        }
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                createdQuery.setParameter(key, params.get(key));
            }
        }
        return createdQuery;
    }

    //====== End Build Query Coding ======

    //====== Create Coding ======
    @Transactional
    public T insert(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public List<T> insert(List<T> listEntities, int batchSize) {
        if (listEntities != null && !listEntities.isEmpty()) {
            for (int i = 0; i < listEntities.size(); i++) {
                insert(listEntities.get(i));
                if (i % batchSize == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }
        }
        return listEntities;
    }

    @Transactional
    public List<T> insert(List<T> listEntities) {
        return insert(listEntities, 50);
    }

    //====== End Create Coding ======

    //====== Research Coding ======

    public T findOne(ID id) {
        return (T) entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        Query createQuery = entityManager.createQuery("From " + this.entityClass.getSimpleName());
        return createQuery.getResultList();
    }

    public long countAll() {
        String queryStr = "SELECT COUNT(e) from " + entityClass.getSimpleName() + " e";
        Query createQuery = entityManager.createQuery(queryStr);
        return (Long) createQuery.getSingleResult();
    }

    public long count(String jpaQuery, boolean isNative, Map<String, Object> params) {
        Query createQuery = buildQueryHasParameters(jpaQuery, isNative, params, null);
        return ((Number) createQuery.getSingleResult()).longValue();
    }

    //====== End Research Coding ======

    //====== Update Coding ======
    @Transactional
    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Transactional
    public List<T> update(List<T> listEntities, int batchSize) {
        if (listEntities != null && !listEntities.isEmpty()) {
            for (int i = 0; i < listEntities.size(); i++) {
                update(listEntities.get(i));
                if (i % batchSize == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }
        }
        return listEntities;
    }

    @Transactional
    public List<T> update(List<T> listEntities) {
        return update(listEntities, 50);
    }

    @Transactional
    public int update(CriteriaUpdate criUpdate) {
        Query query = entityManager.createQuery(criUpdate);
        return query.executeUpdate();
    }

    @Transactional
    public int update(String queryStr, boolean isNative) {
        Query query = buildQuery(queryStr, isNative);
        return query.executeUpdate();
    }

    //====== End Update Coding ======

    //====== Delete Coding ======

    @Transactional
    public int delete(T entity) {
        entityManager.remove(entity);
        return 1;
    }

    @Transactional
    public int delete(CriteriaDelete criDelete) {
        Query query = entityManager.createQuery(criDelete);
        return query.executeUpdate();
    }

    @Transactional
    public int deleteByQuery(String queryStr, boolean isNative) {
        Query query = buildQuery(queryStr, isNative);
        return query.executeUpdate();
    }

    @Transactional
    public int deleteByQuery(String queryStr, boolean isNative, Map<String, Object> params) {
        Query query = buildQueryHasParameters(queryStr, isNative, params);
        return query.executeUpdate();
    }

    @Transactional
    public int delete(ID id) {
        String queryStr = "Delete from "+ entityClass.getSimpleName() +" e where e.id = "+id;
        Query query = buildQuery(queryStr, false);
        return query.executeUpdate();
    }

    //====== End Delete Coding ======
}
