package org.wit.foodchallengeplace.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.foodchallengeplace.R
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengesplaceBinding
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import timber.log.Timber.i

class FoodchallengePlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodchallengesplaceBinding
    var foodchallengeplace = FoodchallengePlaceModel()
    lateinit var app : MainApp

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
}