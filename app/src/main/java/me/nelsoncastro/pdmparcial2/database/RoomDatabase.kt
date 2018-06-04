package me.nelsoncastro.pdmparcial2.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.content.Context
import me.nelsoncastro.pdmparcial2.entities.Nouvelle

@Database(entities = arrayOf(Nouvelle::class), version = 1)
abstract class RoomDatabase: android.arch.persistence.room.RoomDatabase() {
    abstract fun nouvelleDao(): NouvelleDao

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