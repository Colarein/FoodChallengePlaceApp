package org.wit.foodchallengeplace.models

import android.net.Uri
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.snapshot.Index
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class FoodchallengePlaceModel(@PrimaryKey(autoGenerate = true)

    var id: Long = 0,
    var title: String = "",
    var restaurant: String = "",
    var difficulty: String = "",
    // var challengePicker: Array<Int> = emptyArray(),
    var image: Uri = Uri.EMPTY,
    @Embedded var location : Location = Location()): Parcelable



@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
