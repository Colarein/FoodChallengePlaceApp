package org.wit.foodchallengeplace.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.foodchallengeplace.R
import org.wit.foodchallengeplace.adapters.FoodchallengePlaceAdapter
import org.wit.foodchallengeplace.adapters.FoodchallengePlaceListener
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengeListBinding
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel

class FoodchallengeListActivity : AppCompatActivity(), FoodchallengePlaceListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityFoodchallengeListBinding

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
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, FoodchallengePlaceActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFoodchallengeplaceClick(foodchallengeplace: FoodchallengePlaceModel) {
        val launcherIntent = Intent(this, FoodchallengePlaceListener::class.java)
        startActivityForResult(launcherIntent,0)
    }
}