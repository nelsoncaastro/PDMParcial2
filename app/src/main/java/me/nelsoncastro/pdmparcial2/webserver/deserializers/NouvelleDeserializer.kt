package me.nelsoncastro.pdmparcial2.webserver.deserializers

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
                    if (json.asJsonObject.get("description") != null) json.asJsonObject.get("description").asString else " ",
                    json.asJsonObject.get("game").asString,
                    if (json.asJsonObject.get("coverImage") != null) json.asJsonObject.get("coverImage").asString else "https://i.redditmedia.com/ETxJKtze1TEZVxiSnSnhYQhuCMA8JZyI9SqDih32Dw4.jpg?s=b59c58f0371a54741efe66b89c546dd9",
                    json.asJsonObject.get("created_date").asString)
    }
}