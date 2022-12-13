package org.wit.foodchallengeplace.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.snapshot.Index
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodchallengePlaceModel(
    var id: Long = 0,
    var title: String = "",
    var restaurant: String = "",
    var difficulty: String = "",
    var challengePicker: Array<Int> = emptyArray(),
    var image: Uri = Uri.EMPTY,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
