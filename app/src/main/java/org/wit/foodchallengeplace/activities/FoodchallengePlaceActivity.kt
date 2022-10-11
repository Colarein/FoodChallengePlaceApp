package org.wit.foodchallengeplace.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.foodchallengeplace.R
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengesplaceBinding
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import org.wit.foodchallengeplace.helpers.showImagePicker
import timber.log.Timber.i

class FoodchallengePlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodchallengesplaceBinding
    var foodchallengeplace = FoodchallengePlaceModel()
    lateinit var app : MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityFoodchallengesplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.foodchallengetoolbarAdd.title = title
        setSupportActionBar(binding.foodchallengetoolbarAdd)
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
            binding.chooseImage.setText(R.string.change_foodchallengeplace_image)
            Picasso.get()
                .load(foodchallengeplace.image)
                .into(binding.foodchallengeplaceImage)
        }
        binding.btnAdd.setOnClickListener() {
            foodchallengeplace.title = binding.foodchallengeplaceTitle.text.toString()
            foodchallengeplace.restaurant = binding.restaurant.text.toString()
            foodchallengeplace.address = binding.address.text.toString()
            foodchallengeplace.difficulty = binding.difficulty.text.toString()
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

        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_foodchallengeplace, menu)
    return super.onCreateOptionsMenu(menu)
}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
        R.id.item_cancel -> { finish()
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
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}