package org.wit.foodchallengeplace.activities

import android.content.Intent
//import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.NumberPicker
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.squareup.picasso.MemoryPolicy
import org.wit.foodchallengeplace.R
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengesplaceBinding
import org.wit.foodchallengeplace.helpers.showImagePicker
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.Location
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import timber.log.Timber.i


class FoodchallengePlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodchallengesplaceBinding
    var foodchallengeplace = FoodchallengePlaceModel()
    lateinit var app : MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
//    var location = Location(52.245696, -7.139102, 15f)
    var edit = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodchallengesplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.foodchallengetoolbarAdd.title = title
        setSupportActionBar(binding.foodchallengetoolbarAdd)

//        binding.difficultyPicker.maxValue = 5;
//        binding.difficultyPicker.minValue = 1;

        app = application as MainApp

        i("Foodchallenge Place Activity started...")


        if (intent.hasExtra("foodchallengeplace_edit")) {
            edit = true
            foodchallengeplace = intent.extras?.getParcelable("foodchallengeplace_edit")!!
            binding.foodchallengeplaceTitle.setText(foodchallengeplace.title)
            binding.restaurant.setText(foodchallengeplace.restaurant)
            binding.address.setText(foodchallengeplace.address)
            binding.difficulty.setText(foodchallengeplace.difficulty)
            binding.btnAdd.setText(R.string.save_foodchallengeplace)
            Picasso.get()
                .load(foodchallengeplace.image)
                .into(binding.foodchallengeplaceImage)
            if (foodchallengeplace.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_foodchallengeplace_image)
            }
        }
        binding.btnAdd.setOnClickListener() {
            foodchallengeplace.title = binding.foodchallengeplaceTitle.text.toString()
            foodchallengeplace.restaurant = binding.restaurant.text.toString()
            foodchallengeplace.address = binding.address.text.toString()
            foodchallengeplace.difficulty = binding.difficulty.text.toString()
//            foodchallengeplace.difficulty = binding.difficulty.text.toString().toInt()
            if (foodchallengeplace.title.isEmpty()) {
                Snackbar
                    .make(it, R.string.enter_foodchallengeplace_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.foodchallengeplaces.update(foodchallengeplace.copy())
                } else {
                    app.foodchallengeplaces.create(foodchallengeplace.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
            i("Select image")
        }

        binding.foodchallengeplaceLocation.setOnClickListener {
            i ("Set Location Pressed")
        }

        binding.foodchallengeplaceLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (foodchallengeplace.zoom != 0f) {
                location.lat =  foodchallengeplace.lat
                location.lng = foodchallengeplace.lng
                location.zoom = foodchallengeplace.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_foodchallengeplace, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.foodchallengeplaces.delete(foodchallengeplace)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            foodchallengeplace.image = result.data!!.data!!
                            Picasso.get()
                                .load(foodchallengeplace.image)
                                .into(binding.foodchallengeplaceImage)
                            binding.chooseImage.setText(R.string.change_foodchallengeplace_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            foodchallengeplace.lat = location.lat
                            foodchallengeplace.lng = location.lng
                            foodchallengeplace.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}