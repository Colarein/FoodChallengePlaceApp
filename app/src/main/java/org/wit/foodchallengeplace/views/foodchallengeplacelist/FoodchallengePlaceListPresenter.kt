package org.wit.foodchallengeplace.views.foodchallengeplacelist

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.foodchallengeplace.activities.FoodchallengePlaceMapsActivity
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import org.wit.foodchallengeplace.views.foodchallengeplace.FoodchallengePlaceView

class FoodchallengePlaceListPresenter(val view: FoodchallengePlaceListView) {

    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getFoodchallengeplaces() = app.foodchallengeplaces.findAll()

    fun doAddFoodchallengeplace() {
        val launcherIntent = Intent(view, FoodchallengePlaceView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditFoodchallengeplace(foodchallengeplace: FoodchallengePlaceModel) {
        val launcherIntent = Intent(view, FoodchallengePlaceView::class.java)
        launcherIntent.putExtra("foodchallengeplace_edit", foodchallengeplace)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun doShowFoodchallengeplacesMap() {
        val launcherIntent = Intent(view, FoodchallengePlaceMapsActivity::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { getFoodchallengeplaces() }
    }
    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }
}