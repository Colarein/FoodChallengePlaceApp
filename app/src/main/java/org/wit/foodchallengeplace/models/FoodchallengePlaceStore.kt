package org.wit.foodchallengeplace.models

interface FoodchallengePlaceStore {
    suspend fun findAll(): List<FoodchallengePlaceModel>
    suspend fun findById(fbId: String) : FoodchallengePlaceModel?
    suspend fun create(foodchallengeplace: FoodchallengePlaceModel)
    suspend fun update (foodchallengeplace: FoodchallengePlaceModel)
    suspend fun delete (foodchallengeplace: FoodchallengePlaceModel)
    suspend fun clear()
}