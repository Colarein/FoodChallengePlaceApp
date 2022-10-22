package org.wit.foodchallengeplace.main

import android.app.Application
import org.wit.foodchallengeplace.models.FoodchallengePlaceMemStore
import org.wit.foodchallengeplace.models.FoodchallengePlaceStore
import org.wit.foodchallengeplace.models.FoodchallengePlaceJSONStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var foodchallengeplaces: FoodchallengePlaceStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        foodchallengeplaces = FoodchallengePlaceJSONStore(applicationContext)
        i("Foodchallenge Place started")
    }
}