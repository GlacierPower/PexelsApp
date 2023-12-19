package com.glacierpower.pexelsapp.data.service

import com.glacierpower.pexelsapp.data.service.response.Photo
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.util.ArrayList

class GsonDeserializador : JsonDeserializer<List<Photo>> {
    var fotos: MutableList<Photo> = ArrayList()

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<Photo> {
        fotos.clear()
        val photos = json.asJsonObject["photos"].asJsonArray
        for (i in 0 until photos.size()) {
            val id = photos[i].asJsonObject["id"].asInt
            val width = photos[i].asJsonObject["width"].asInt
            val height = photos[i].asJsonObject["height"].asInt
            val url = photos[i].asJsonObject["url"].asString
            val photographer = photos[i].asJsonObject["photographer"].asString
            val original = photos[i].asJsonObject["src"].asJsonObject["original"].asString
            val large = photos[i].asJsonObject["src"].asJsonObject["large"].asString
            val large2x = photos[i].asJsonObject["src"].asJsonObject["large2x"].asString
            val medium = photos[i].asJsonObject["src"].asJsonObject["medium"].asString
            val small = photos[i].asJsonObject["src"].asJsonObject["small"].asString
            val portrait = photos[i].asJsonObject["src"].asJsonObject["portrait"].asString
            val landscape = photos[i].asJsonObject["src"].asJsonObject["landscape"].asString
            val tiny = photos[i].asJsonObject["src"].asJsonObject["tiny"].asString
            val currentFoto = Photo(id, width, height, url, photographer, original, large, large2x, medium, small, portrait, landscape, tiny)
            if (!fotos.contains(currentFoto)) fotos.add(currentFoto)
        }
        return fotos
    }
}