package me.nelsoncastro.pdmparcial2.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import me.nelsoncastro.pdmparcial2.entities.Joueur

@Dao
interface JoueurDao {

    @Query("SELECT * FROM joueur ORDER BY name ASC")
    fun getAll(): LiveData<List<Joueur>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(joueur: Joueur)

    @Query("SELECT * FROM joueur WHERE game=:jeux ORDER BY name ASC")
    fun getJoueurByJeux(jeux: String): LiveData<List<Joueur>>

    @Query("DELETE FROM joueur")
    fun deleteAll()
}