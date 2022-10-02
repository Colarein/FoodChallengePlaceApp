package org.wit.foodchallengeplace.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengesplaceBinding
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import timber.log.Timber
import timber.log.Timber.i

class FoodchallengePlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodchallengesplaceBinding
    var foodchallengeplace = FoodchallengePlaceModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodchallengesplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Foodchallenge Place Activity started...")

        binding.btnAdd.setOnClickListener() {
            foodchallengeplace.title = binding.foodchallengeplaceTitle.text.toString()
            if (foodchallengeplace.title.isNotEmpty()) {
                i("add Button Pressed: $foodchallengeplace.title")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}