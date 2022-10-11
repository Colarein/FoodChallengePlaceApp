package org.wit.foodchallengeplace.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodchallengePlaceModel(
    var id: Long = 0,
    var title: String = "",
    var restaurant: String = "",
    var address: String = "",
    var difficulty: String = "",
    var image: Uri = Uri.EMPTY) : Parcelable
