package me.nelsoncastro.pdmparcial2.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "nouvelle")
data class Nouvelle(@PrimaryKey @ColumnInfo(name = "id") var _id: String = "",
                    @ColumnInfo(name = "title") var title: String = "",
                    @ColumnInfo(name = "body") var body: String = "",
                    @ColumnInfo(name = "game") var game: String = "",
                    @ColumnInfo(name = "image") var coverImage: String = "",
                    @ColumnInfo(name = "date") var created_date: String = "",
                    @ColumnInfo(name = "version") var __v: String = "") {
}