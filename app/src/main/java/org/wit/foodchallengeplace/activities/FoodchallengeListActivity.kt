package org.wit.foodchallengeplace.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import org.wit.foodchallengeplace.R
import org.wit.foodchallengeplace.adapters.FoodchallengePlaceAdapter
import org.wit.foodchallengeplace.adapters.FoodchallengePlaceListener
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengeListBinding
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel

class FoodchallengeListActivity : AppCompatActivity(), FoodchallengePlaceListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityFoodchallengeListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapsIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodchallengeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = FoodchallengePlaceAdapter(app.foodchallengeplaces.findAll(), this)
        loadFoodchallengeplaces()

        registerRefreshCallback()
        registerMapCallback()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, FoodchallengePlaceActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, FoodchallengePlaceMapsActivity::class.java)
                mapsIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFoodchallengeplaceClick(foodchallengeplace: FoodchallengePlaceModel) {
        val launcherIntent = Intent(this, FoodchallengePlaceActivity::class.java)
        launcherIntent.putExtra("foodchallengeplace_edit", foodchallengeplace)
        refreshIntentLauncher.launch(launcherIntent)
    }
    private fun registerMapCallback() {
        mapsIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { }
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadFoodchallengeplaces() }
    }

    private fun loadFoodchallengeplaces() {
        showFoodchallengeplaces(app.foodchallengeplaces.findAll())
    }

    fun showFoodchallengeplaces (foodchallengeplaces: List<FoodchallengePlaceModel>) {
        binding.recyclerView.adapter = FoodchallengePlaceAdapter(foodchallengeplaces, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}