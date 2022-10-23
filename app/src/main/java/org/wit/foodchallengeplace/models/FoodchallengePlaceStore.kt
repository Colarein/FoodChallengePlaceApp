package org.wit.foodchallengeplace.models

interface FoodchallengePlaceStore {
    fun findAll(): List<FoodchallengePlaceModel>
    fun findById(id:Long) : FoodchallengePlaceModel?
    fun create(foodchallengeplace: FoodchallengePlaceModel)
    fun update (foodchallengeplace: FoodchallengePlaceModel)
    fun delete (foodchallengeplace: FoodchallengePlaceModel)
}