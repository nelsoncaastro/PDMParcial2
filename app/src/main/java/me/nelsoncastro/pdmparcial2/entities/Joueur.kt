package me.nelsoncastro.pdmparcial2.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "joueur")
data class Joueur(@PrimaryKey @ColumnInfo(name = "id") var _id: String = "",
                  @ColumnInfo(name = "name") var name: String = "",
                  @ColumnInfo(name = "biografia") var biografia: String = "",
                  @ColumnInfo(name = "avatar") var avatar: String = "",
                  @ColumnInfo(name = "game") var game: String = "") {
}