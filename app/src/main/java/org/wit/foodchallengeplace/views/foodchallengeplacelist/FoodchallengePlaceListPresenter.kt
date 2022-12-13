package org.wit.foodchallengeplace.views.foodchallengeplacelist

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import org.wit.foodchallengeplace.views.foodchallengeplace.FoodchallengePlaceView
import org.wit.foodchallengeplace.views.map.FoodchallengePlaceMapView
import timber.log.Timber

class FoodchallengePlaceListPresenter(val view: FoodchallengePlaceListView) {
    var app: MainApp = view.application as MainApp
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var editIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>

    init {
        app = view.application as MainApp
        registerEditCallback()
        registerMapCallback()
        registerRefreshCallback()
        // registerImagePickerCallback()
    }

    fun getFoodchallengeplaces() = app.foodchallengeplaces.findAll()

    fun doAddFoodchallengeplace() {
        val launcherIntent = Intent(view, FoodchallengePlaceView::class.java)
        editIntentLauncher.launch(launcherIntent)
    }

    fun doEditFoodchallengeplace(foodchallengeplace: FoodchallengePlaceModel) {
        val launcherIntent = Intent(view, FoodchallengePlaceView::class.java)
        launcherIntent.putExtra("Foodchallengeplace_edit", foodchallengeplace)
        editIntentLauncher.launch(launcherIntent)
    }

    fun doShowFoodchallengeplacesMap() {
        val launcherIntent = Intent(view, FoodchallengePlaceMapView::class.java)
        editIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { getFoodchallengeplaces() }
    }

    private fun registerEditCallback() {
        editIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { }

    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { }
    }

//    private fun registerImagePickerCallback() {
//        imageIntentLauncher =
//            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
//            { result ->
//                when (result.resultCode) {
//                    AppCompatActivity.RESULT_OK -> {
//                        if (result.data != null) {
//                            Timber.i("Got Result ${result.data!!.data}")
//                            foodchallengeplace.image = result.data!!.data!!
//                            view.updateImage(foodchallengeplace.image)
//                        }
//                    }
//                    AppCompatActivity.RESULT_CANCELED -> {}
//                    else -> {}
//                }
//            }
//    }
}