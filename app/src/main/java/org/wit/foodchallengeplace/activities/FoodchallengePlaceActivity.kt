package org.wit.foodchallengeplace.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengesplaceBinding
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import timber.log.Timber
import timber.log.Timber.i

class FoodchallengePlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodchallengesplaceBinding
    var foodchallengeplace = FoodchallengePlaceModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodchallengesplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Foodchallenge Place Activity started...")
        binding.btnAdd.setOnClickListener() {
            foodchallengeplace.title = binding.foodchallengeplaceTitle.text.toString()
            foodchallengeplace.restaurant = binding.restaurant.text.toString()
            foodchallengeplace.address = binding.address.text.toString()
            foodchallengeplace.difficulty = binding.difficulty.text.toString()
            if (foodchallengeplace.title.isNotEmpty()) {
                app.foodchallengeplaces.add(foodchallengeplace.copy())
                i("add Button Pressed: $foodchallengeplace")
                for (i in app.foodchallengeplaces.indices)
                { i("Food Challenge[$i]:${this.app.foodchallengeplaces[i]}") }
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}