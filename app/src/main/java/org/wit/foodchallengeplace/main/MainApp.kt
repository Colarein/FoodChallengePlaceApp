package org.wit.foodchallengeplace.main

import android.app.Application
import org.wit.foodchallengeplace.models.FoodchallengePlaceMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    val foodchallengeplaces = FoodchallengePlaceMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Food Challenge Place started")
    }
}