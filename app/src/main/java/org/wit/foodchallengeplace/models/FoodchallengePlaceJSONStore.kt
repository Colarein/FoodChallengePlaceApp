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

fun generateRandomId(): String {
    return Random().nextLong().toString()
}

class FoodchallengePlaceJSONStore (private val context: Context) : FoodchallengePlaceStore {

    var foodchallengeplaces = mutableListOf<FoodchallengePlaceModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override suspend fun findAll(): MutableList<FoodchallengePlaceModel> {
        logAll()
        return foodchallengeplaces
    }

    override suspend fun create(foodchallengeplace: FoodchallengePlaceModel) {
        foodchallengeplace.fbId = generateRandomId()
        foodchallengeplaces.add(foodchallengeplace)
        serialize()
    }


    override suspend fun update(foodchallengeplace: FoodchallengePlaceModel) {
        val foodchallengeplacesList = findAll() as ArrayList<FoodchallengePlaceModel>
        var foundFoodchallengeplace: FoodchallengePlaceModel? = foodchallengeplacesList.find { p -> p.fbId == foodchallengeplace.fbId }
        if (foundFoodchallengeplace != null) {
            foundFoodchallengeplace.title = foodchallengeplace.title
            foundFoodchallengeplace.restaurant = foodchallengeplace.restaurant
            foundFoodchallengeplace.difficulty = foodchallengeplace.difficulty
            // foundFoodchallengeplace.challengePicker = foodchallengeplace.challengePicker
            foundFoodchallengeplace.image = foodchallengeplace.image
            foundFoodchallengeplace.lat = foodchallengeplace.lat
            foundFoodchallengeplace.lng = foodchallengeplace.lng
            foundFoodchallengeplace.zoom = foodchallengeplace.zoom
        }
        serialize()
    }

    override suspend fun delete(foodchallengeplace: FoodchallengePlaceModel) {
        val foundFoodchallengeplace: FoodchallengePlaceModel? = foodchallengeplaces.find{ it.fbId == foodchallengeplace.fbId }
        foodchallengeplaces.remove(foundFoodchallengeplace)
        serialize()
    }

    override suspend fun findById(fbId: String) : FoodchallengePlaceModel? {
        val foundFoodchallengeplace: FoodchallengePlaceModel? = foodchallengeplaces.find { it.fbId == fbId }
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

    override suspend fun clear(){
        foodchallengeplaces.clear()
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