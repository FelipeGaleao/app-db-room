package com.meicansoftware.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM Usuario WHERE id = :id LIMIT 1")
    Usuario getUser(int id);

    @Query("SELECT * FROM Usuario")
    List<Usuario> getAll();

    @Insert
    void InsertAll(Usuario ... usuarios);

    @Update
    void update(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

}
