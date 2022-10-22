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
            foundFoodchallengeplace.address = foodchallengeplace.address
            foundFoodchallengeplace.difficulty = foodchallengeplace.difficulty
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

    fun logAll() {
        foodchallengeplaces.forEach{ i("${it}") }
    }
}