package org.wit.foodchallengeplace.models

interface FoodchallengePlaceStore {
    fun findAll(): List<FoodchallengePlaceModel>
    fun create(foodchallengeplace: FoodchallengePlaceModel)
    fun update (foodchallengeplace: FoodchallengePlaceModel)
}