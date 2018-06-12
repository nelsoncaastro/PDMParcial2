package me.nelsoncastro.pdmparcial2.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "categorie")
data class Categorie(@PrimaryKey @ColumnInfo(name = "name") var name: String= "") {
}