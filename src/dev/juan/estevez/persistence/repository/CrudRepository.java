package dev.juan.estevez.persistence.repository;

import java.util.List;

/**
 * 
 * @author Juan Carlos Estevez Vargas.
 */
public interface CrudRepository<T, ID> {

    int create(T entity);

    T findById(ID id);

    List<T> findAll();

    int update(T entity);

    void deleteById(ID id);

}
