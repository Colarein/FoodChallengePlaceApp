package org.wit.foodchallengeplace.models

import timber.log.Timber.i

class FoodchallengePlaceMemStore : FoodchallengePlaceStore {

    val foodchallengeplaces = ArrayList<FoodchallengePlaceModel>()

    override fun findAll(): List<FoodchallengePlaceModel> {
        return foodchallengeplaces
    }

    override fun create(foodchallengeplace: FoodchallengePlaceModel) {
        foodchallengeplaces.add(foodchallengeplace)
        logAll()
    }

    fun logAll() {
        foodchallengeplaces.forEach{ i("${it}") }
    }
}