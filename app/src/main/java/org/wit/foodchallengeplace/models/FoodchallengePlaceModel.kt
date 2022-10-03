package org.wit.foodchallengeplace.models

data class FoodchallengePlaceModel(
    var id: Long = 0,
    var title: String = "",
    var restaurant: String = "",
    var address: String = "",
    var difficulty: String = ""
)
