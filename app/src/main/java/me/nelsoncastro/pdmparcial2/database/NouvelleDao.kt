package me.nelsoncastro.pdmparcial2.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import me.nelsoncastro.pdmparcial2.entities.Nouvelle

@Dao
interface NouvelleDao {

    @Query("SELECT * FROM nouvelle ORDER BY date DESC")
    fun getAll(): LiveData<List<Nouvelle>>

    @Query("SELECT * FROM nouvelle WHERE game =:jeux ORDER BY date DESC")
    fun getNouvelleByJeux(jeux: String): LiveData<List<Nouvelle>>

    @Query("SELECT * FROM nouvelle WHERE title LIKE :titre ORDER BY date DESC")
    fun getNouvelleByTitre(titre: String): LiveData<List<Nouvelle>>

    @Query("UPDATE nouvelle SET favoris=:value WHERE id=:id")
    fun setFavoris(value: Int, id: String)

    @Query("SELECT * FROM nouvelle WHERE favoris=1 ORDER BY date DESC")
    fun getAllNouvelleFavoris(): LiveData<List<Nouvelle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(nouvelle: Nouvelle)

    @Query("DELETE FROM nouvelle")
    fun deleteAll()
}