package org.wit.foodchallengeplace.main

import android.app.Application
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    val foodchallengeplaces = ArrayList<FoodchallengePlaceModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Food Challenge Place started")
//        foodchallengeplaces.add(FoodchallengePlaceModel(1, "The Big Boi Burger", "O'Dwyer's","Cork","5"))
//        foodchallengeplaces.add(FoodchallengePlaceModel(2, "Mount Fuji Ramen Bowl", "Miyazaki's", "Cork","1"))
//        foodchallengeplaces.add(FoodchallengePlaceModel(3, "The Godfather", "Tony's Bistro","Cork","3"))
    }
}