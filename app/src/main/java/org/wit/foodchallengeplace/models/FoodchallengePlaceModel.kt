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
    var challengePicker: String = "",
    var image: Uri = Uri.EMPTY,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
