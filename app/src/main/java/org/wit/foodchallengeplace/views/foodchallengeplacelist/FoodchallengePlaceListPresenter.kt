package org.wit.foodchallengeplace.views.foodchallengeplacelist

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import org.wit.foodchallengeplace.views.login.LoginView
import org.wit.foodchallengeplace.views.foodchallengeplace.FoodchallengePlaceView
import org.wit.foodchallengeplace.views.map.FoodchallengePlaceMapView

class FoodchallengePlaceListPresenter(private val view: FoodchallengePlaceListView) {

    var app: MainApp = view.application as MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var editIntentLauncher : ActivityResultLauncher<Intent>

    init {
        registerEditCallback()
        registerRefreshCallback()
    }

    suspend fun getFoodchallengeplaces() = app.foodchallengeplaces.findAll()

    fun doAddFoodchallengeplace() {
        val launcherIntent = Intent(view, FoodchallengePlaceView::class.java)
        editIntentLauncher.launch(launcherIntent)
    }

    fun doEditFoodchallengeplace(foodchallengeplace: FoodchallengePlaceModel) {
        val launcherIntent = Intent(view, FoodchallengePlaceView::class.java)
        launcherIntent.putExtra("foodchallengeplace_edit", foodchallengeplace)
        editIntentLauncher.launch(launcherIntent)
    }

    fun doShowFoodchallengeplacesMap() {
        val launcherIntent = Intent(view, FoodchallengePlaceMapView::class.java)
        editIntentLauncher.launch(launcherIntent)
    }

    suspend fun doLogout(){
        FirebaseAuth.getInstance().signOut()
        app.foodchallengeplaces.clear()
        val launcherIntent = Intent(view, LoginView::class.java)
        editIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {
                GlobalScope.launch(Dispatchers.Main){
                    getFoodchallengeplaces()
                }
            }
    }
    private fun registerEditCallback() {
        editIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }

    }
}