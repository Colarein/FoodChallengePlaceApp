package org.wit.foodchallengeplace.views.foodchallengeplace

import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengesplaceBinding
import org.wit.foodchallengeplace.helpers.showImagePicker
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import org.wit.foodchallengeplace.models.Location
import org.wit.foodchallengeplace.views.location.EditLocationView
import timber.log.Timber

@Suppress("DEPRECATION")
class FoodchallengePlacePresenter(private val view: FoodchallengePlaceView) {

    var foodchallengeplace = FoodchallengePlaceModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityFoodchallengesplaceBinding = ActivityFoodchallengesplaceBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    var edit = false

    init {
        if (view.intent.hasExtra("foodchallengeplace_edit")) {
            edit = true
            foodchallengeplace = view.intent.extras?.getParcelable("foodchallengeplace_edit")!!
            view.showFoodchallengeplaces(foodchallengeplace)
        }
        registerImagePickerCallback()
        registerMapCallback()
        setupChallengePicker()
    }

    fun doAddOrSave(title: String, restaurant: String, address: String, difficulty: String, challengePicker: Int ) {
        foodchallengeplace.title = title
        foodchallengeplace.restaurant = restaurant
        foodchallengeplace.address = address
        foodchallengeplace.difficulty = difficulty
        foodchallengeplace.challengePicker= challengePicker
        if (edit) {
            app.foodchallengeplaces.update(foodchallengeplace)
        } else {
            app.foodchallengeplaces.create(foodchallengeplace)
        }
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.foodchallengeplaces.delete(foodchallengeplace)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher)
    }

    fun doSetLocation() {
        val location = Location(51.8985, -8.4756, 15f)
        if (foodchallengeplace.zoom != 0f) {
            location.lat =  foodchallengeplace.lat
            location.lng = foodchallengeplace.lng
            location.zoom = foodchallengeplace.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerImagePickerCallback() {

        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            foodchallengeplace.image = result.data!!.data!!
                            view.updateImage(foodchallengeplace.image)
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            foodchallengeplace.lat = location.lat
                            foodchallengeplace.lng = location.lng
                            foodchallengeplace.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }

            }
    }

    private fun setupChallengePicker() {
        val challengePicker = binding.challengePicker
        val values = arrayOf("Spicy", "BIG", "Speed")
        challengePicker.minValue = 0
        challengePicker.maxValue = values.size - 1
        challengePicker.displayedValues = values
        challengePicker.wrapSelectorWheel = true
        challengePicker.setOnValueChangedListener { _, oldVal, newVal ->
            val text = "Changed from " + values[oldVal] + " to " + values[newVal]
           // Toast.makeText(this@FoodchallengePlacePresenter, text, Toast.LENGTH_SHORT).show()
        }
    }

}