package me.nelsoncastro.pdmparcial2.webserver.deserializers

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
        val favoris: ArrayList<String> = ArrayList()
        json.asJsonObject.get("favoriteNews").asJsonArray?.forEach {
            favoris.add(it.asJsonObject.asString)
        }
        etudiant.favoriteNews = if(favoris.isEmpty()) arrayListOf("") else favoris
        return etudiant
    }
}