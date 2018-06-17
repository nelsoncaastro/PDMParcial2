package me.nelsoncastro.pdmparcial2.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import me.nelsoncastro.pdmparcial2.entities.Etudiant

@Dao
interface EtudiantDao {

    @Query("SELECT * FROM etudiant ORDER BY user ASC")
    fun getAll(): LiveData<List<Etudiant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(etudiant: Etudiant)

    @Query("DELETE FROM etudiant")
    fun deleteAll()
}