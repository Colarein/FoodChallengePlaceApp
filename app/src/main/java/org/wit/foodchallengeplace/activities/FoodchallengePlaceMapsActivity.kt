package org.wit.foodchallengeplace.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import org.wit.foodchallengeplace.R
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengeplaceMapsBinding

class FoodchallengePlaceMapsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityFoodchallengeplaceMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodchallengeplaceMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController =
            findNavController(R.id.nav_host_fragment_content_foodchallenge_place_maps)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_foodchallenge_place_maps)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}