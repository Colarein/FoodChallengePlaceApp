package org.wit.foodchallengeplace.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class FoodchallengePlaceMemStore : FoodchallengePlaceStore {

    val foodchallengeplaces = ArrayList<FoodchallengePlaceModel>()

    override fun findAll(): List<FoodchallengePlaceModel> {
        return foodchallengeplaces
    }

    override fun create(foodchallengeplace: FoodchallengePlaceModel) {
        foodchallengeplace.id = getId()
        foodchallengeplaces.add(foodchallengeplace)
        logAll()
    }

    override fun update(foodchallengeplace: FoodchallengePlaceModel) {
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
            logAll()
        }
    }


    override fun delete(foodchallengeplace: FoodchallengePlaceModel) {
        foodchallengeplaces.remove(foodchallengeplace)
    }

//    override fun deleteAll(foodchallengeplace: FoodchallengePlaceModel) {
//        foodchallengeplaces.removeAll(foodchallengeplaces)
//    }

    override fun findById(id:Long) : FoodchallengePlaceModel? {
        val foundFoodchallengeplace: FoodchallengePlaceModel? = foodchallengeplaces.find { it.id == id }
        return foundFoodchallengeplace
    }

    fun logAll() {
        foodchallengeplaces.forEach{ i("${it}") }
    }
}