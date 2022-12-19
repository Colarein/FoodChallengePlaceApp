package org.wit.foodchallengeplace.main

import android.app.Application
import org.wit.foodchallengeplace.models.FoodchallengePlaceFireStore
//import org.wit.foodchallengeplace.models.FoodchallengePlaceMemStore
import org.wit.foodchallengeplace.models.FoodchallengePlaceStore
//import org.wit.foodchallengeplace.models.FoodchallengePlaceJSONStore
//import org.wit.foodchallengeplace.room.FoodchallengePlaceStoreRoom
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var foodchallengeplaces: FoodchallengePlaceStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        foodchallengeplaces = FoodchallengePlaceFireStore(applicationContext)
        i("Foodchallenge Place started")
    }
}