package org.wit.foodchallengeplace.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodchallengePlaceModel(
    var id: Long = 0,
    var title: String = "",
    var restaurant: String = "",
    var address: String = "",
    var difficulty: String = "") : Parcelable
