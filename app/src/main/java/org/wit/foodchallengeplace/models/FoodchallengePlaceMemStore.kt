package org.wit.foodchallengeplace.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class FoodchallengePlaceMemStore : FoodchallengePlaceStore {

    val foodchallengeplaces = ArrayList<FoodchallengePlaceModel>()

    override suspend fun findAll(): List<FoodchallengePlaceModel> {
        return foodchallengeplaces
    }

    override suspend fun create(foodchallengeplace: FoodchallengePlaceModel) {
        foodchallengeplace.id = getId()
        foodchallengeplaces.add(foodchallengeplace)
        logAll()
    }

    override suspend fun update(foodchallengeplace: FoodchallengePlaceModel) {
        var foundFoodchallengeplace: FoodchallengePlaceModel? = foodchallengeplaces.find { p -> p.id == foodchallengeplace.id }
        if (foundFoodchallengeplace != null) {
            foundFoodchallengeplace.title = foodchallengeplace.title
            foundFoodchallengeplace.restaurant = foodchallengeplace.restaurant
            foundFoodchallengeplace.difficulty = foodchallengeplace.difficulty
            // foundFoodchallengeplace.challengePicker = foodchallengeplace.challengePicker
            foundFoodchallengeplace.image = foodchallengeplace.image
            foundFoodchallengeplace.lat = foodchallengeplace.lat
            foundFoodchallengeplace.lng = foodchallengeplace.lng
            foundFoodchallengeplace.zoom = foodchallengeplace.zoom
            logAll();
        }
    }


    override suspend fun delete(foodchallengeplace: FoodchallengePlaceModel) {
        foodchallengeplaces.remove(foodchallengeplace)
    }

//    override fun deleteAll(foodchallengeplace: FoodchallengePlaceModel) {
//        foodchallengeplaces.removeAll(foodchallengeplaces)
//    }

    override suspend fun findById(fbId: String) : FoodchallengePlaceModel? {
        val foundFoodchallengeplace: FoodchallengePlaceModel? = foodchallengeplaces.find { it.fbId == fbId }
        return foundFoodchallengeplace
    }

    fun logAll() {
        foodchallengeplaces.forEach{ i("${it}") }
    }

    override suspend fun clear(){
        foodchallengeplaces.clear()
    }
}