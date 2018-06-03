package me.nelsoncastro.pdmparcial2.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import me.nelsoncastro.pdmparcial2.entities.Nouvelle

@Dao
interface NouvelleDao {
    @Query("SELECT * FROM nouvelle")
    fun getAll(): LiveData<List<Nouvelle>>

    @Insert
    fun insert(nouvelle: Nouvelle)

    @Query("DELETE FROM nouvelle")
    fun deleteAll()
}