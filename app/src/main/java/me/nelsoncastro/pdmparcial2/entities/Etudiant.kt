package me.nelsoncastro.pdmparcial2.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "etudiant")
data class Etudiant(@PrimaryKey @ColumnInfo(name = "id") var _id: String = "",
                    @ColumnInfo(name = "user") var user: String = "",
                    @ColumnInfo(name = "password") var password: String = "",
                    @ColumnInfo(name = "favoriteNews") var favoriteNews: String) {
}