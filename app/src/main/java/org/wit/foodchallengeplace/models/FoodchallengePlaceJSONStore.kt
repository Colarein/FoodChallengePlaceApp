package org.wit.foodchallengeplace.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.foodchallengeplace.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "foodchallengeplaces.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<FoodchallengePlaceModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class FoodchallengePlaceJSONStore (private val context: Context) : FoodchallengePlaceStore {

    var foodchallengeplaces = mutableListOf<FoodchallengePlaceModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<FoodchallengePlaceModel> {
        logAll()
        return foodchallengeplaces
    }

    override fun create(foodchallengeplace: FoodchallengePlaceModel) {
        foodchallengeplace.id = generateRandomId()
        foodchallengeplaces.add(foodchallengeplace)
        serialize()
    }


    override fun update(foodchallengeplace: FoodchallengePlaceModel) {
        val foodchallengeplacesList = findAll() as ArrayList<FoodchallengePlaceModel>
        var foundFoodchallengeplace: FoodchallengePlaceModel? = foodchallengeplacesList.find { p -> p.id == foodchallengeplace.id }
        if (foundFoodchallengeplace != null) {
            foundFoodchallengeplace.title = foodchallengeplace.title
            foundFoodchallengeplace.restaurant = foodchallengeplace.restaurant
            foundFoodchallengeplace.address = foodchallengeplace.address
            foundFoodchallengeplace.difficulty = foodchallengeplace.difficulty
            foundFoodchallengeplace.challengePicker = foodchallengeplace.challengePicker
            foundFoodchallengeplace.image = foodchallengeplace.image
            foundFoodchallengeplace.lat = foodchallengeplace.lat
            foundFoodchallengeplace.lng = foodchallengeplace.lng
            foundFoodchallengeplace.zoom = foodchallengeplace.zoom
        }
        serialize()
    }

    override fun delete(foodchallengeplace: FoodchallengePlaceModel) {
        foodchallengeplaces.remove(foodchallengeplace)
        serialize()
    }

//    override fun deleteAll(foodchallengeplace: FoodchallengePlaceModel) {
//        foodchallengeplaces.removeAll(foodchallengeplaces)
//        serialize()
//    }

    override fun findById(id:Long) : FoodchallengePlaceModel? {
        val foundFoodchallengeplace: FoodchallengePlaceModel? = foodchallengeplaces.find { it.id == id }
        return foundFoodchallengeplace
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(foodchallengeplaces, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        foodchallengeplaces = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        foodchallengeplaces.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}