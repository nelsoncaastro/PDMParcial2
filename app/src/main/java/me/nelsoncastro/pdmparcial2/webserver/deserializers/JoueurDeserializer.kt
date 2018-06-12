package me.nelsoncastro.pdmparcial2.webserver.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import me.nelsoncastro.pdmparcial2.entitieesapi.Joueur
import java.lang.reflect.Type

class JoueurDeserializer: JsonDeserializer<Joueur> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Joueur {
        return Joueur(
                json.asJsonObject.get("_id").asString,
                json.asJsonObject.get("name").asString,
                json.asJsonObject.get("biografia").asString,
                json.asJsonObject.get("avatar").asString,
                json.asJsonObject.get("game").asString)
    }

}