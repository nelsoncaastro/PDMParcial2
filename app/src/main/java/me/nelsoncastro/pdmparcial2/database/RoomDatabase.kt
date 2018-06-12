package me.nelsoncastro.pdmparcial2.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.content.Context
import me.nelsoncastro.pdmparcial2.entities.Categorie
import me.nelsoncastro.pdmparcial2.entities.Etudiant
import me.nelsoncastro.pdmparcial2.entities.Joueur
import me.nelsoncastro.pdmparcial2.entities.Nouvelle

@Database(entities = [(Nouvelle::class), (Joueur::class), (Etudiant::class), (Categorie::class)], version = 2)
abstract class RoomDatabase: android.arch.persistence.room.RoomDatabase() {
    abstract fun nouvelleDao(): NouvelleDao
    abstract fun joueurDao(): JoueurDao
    abstract fun etudiantDao(): EtudiantDao
    abstract fun categorieDao(): CategorieDao

    companion object {
        private var INSTANCE: RoomDatabase? = null

        fun getDatabase(context: Context): RoomDatabase? {
            if (INSTANCE == null){
                synchronized(RoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            RoomDatabase::class.java, "room_database")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}