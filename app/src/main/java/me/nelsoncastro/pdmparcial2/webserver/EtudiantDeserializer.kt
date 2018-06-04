package me.nelsoncastro.pdmparcial2.webserver

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import me.nelsoncastro.pdmparcial2.entitieesapi.Etudiant
import java.lang.reflect.Type
import com.google.gson.reflect.TypeToken
import me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle


class EtudiantDeserializer: JsonDeserializer<Etudiant> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Etudiant {
        val repoJsonObject = json.asJsonObject
        val etudiant = Etudiant(repoJsonObject.get("_id").asString,repoJsonObject.get("user").asString,repoJsonObject.get("password").asString)
        val groupListType = object : TypeToken<ArrayList<Nouvelle>>(){}.type
        val nouvellesfavoris = json.asJsonObject.get("favoriteNews").asJsonArray
        val favoris: ArrayList<Nouvelle> = ArrayList()
        nouvellesfavoris.forEach {
            favoris.add(Nouvelle(
                    it.asJsonObject.get("_id").asString,
                    it.asJsonObject.get("title").asString,
                    it.asJsonObject.get("body").asString,
                    it.asJsonObject.get("description").asString,
                    it.asJsonObject.get("game").asString,
                    it.asJsonObject.get("coverImage").asString,
                    it.asJsonObject.get("created_date").asString
            ))
        }
        etudiant.favoriteNews = favoris
        return etudiant
    }
}