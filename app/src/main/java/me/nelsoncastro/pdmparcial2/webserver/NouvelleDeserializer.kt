package me.nelsoncastro.pdmparcial2.webserver

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle
import java.lang.reflect.Type

class NouvelleDeserializer: JsonDeserializer<Nouvelle> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Nouvelle {
        return Nouvelle(
                    json.asJsonObject.get("_id").asString,
                    json.asJsonObject.get("title").asString,
                    json.asJsonObject.get("body").asString,
                    json.asJsonObject.get("description").asString,
                    json.asJsonObject.get("game").asString,
                    json.asJsonObject.get("coverImage").asString,
                    json.asJsonObject.get("created_date").asString)
    }
}