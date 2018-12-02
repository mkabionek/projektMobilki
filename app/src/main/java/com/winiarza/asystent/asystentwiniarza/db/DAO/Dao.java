package com.winiarza.asystent.asystentwiniarza.db.DAO;

import java.util.ArrayList;

public interface Dao<T> {
    long save(T type);
    void update(T type);
    void delete(T type);
    T get(long id);
    ArrayList<T> getAll();
}

