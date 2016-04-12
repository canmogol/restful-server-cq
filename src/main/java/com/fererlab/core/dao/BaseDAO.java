package com.fererlab.core.dao;


import com.fererlab.core.model.Model;

import javax.ejb.Local;
import java.util.List;


@Local
public interface BaseDAO<T extends Model<PK>, PK> {

    void create(T t);

    void update(T t);

    void delete(T t);

    void delete(PK id);

    T findById(PK id);

    List<T> list();

    List<T> list(Integer index, Integer numberOfRecords);

    Long count();

}
