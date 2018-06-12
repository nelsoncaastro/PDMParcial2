package me.nelsoncastro.pdmparcial2.webserver.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CategorieDeserializer: JsonDeserializer<List<String>> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): List<String>{
        val catty = ArrayList<String>()
        for (x in json.asJsonArray){
           catty.add(x.asString)
        }
        return catty
    }

}