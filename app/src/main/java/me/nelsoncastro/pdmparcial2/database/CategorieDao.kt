package me.nelsoncastro.pdmparcial2.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import me.nelsoncastro.pdmparcial2.entities.Categorie

@Dao
interface CategorieDao {

    @Query("SELECT * FROM categorie ORDER BY name ASC")
    fun getAll(): LiveData<List<Categorie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categorie: Categorie)

    @Query("DELETE FROM categorie")
    fun deleteAll()
}