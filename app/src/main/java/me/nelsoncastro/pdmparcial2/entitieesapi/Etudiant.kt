package me.nelsoncastro.pdmparcial2.entitieesapi

data class Etudiant(var _id: String = "",
                    var user: String = "",
                    var password: String = "") {
    var favoriteNews: ArrayList<Nouvelle>? = null
}