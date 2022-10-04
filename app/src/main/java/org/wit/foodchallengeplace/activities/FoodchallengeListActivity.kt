package org.wit.foodchallengeplace.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.foodchallengeplace.R
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengeListBinding
import org.wit.foodchallengeplace.databinding.CardFoodchallengeplaceBinding
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel

class FoodchallengeListActivity : AppCompatActivity() {
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
        binding.recyclerView.adapter = FoodchallengePlaceAdapter(app.foodchallengeplaces)
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
}

class FoodchallengePlaceAdapter constructor(private var foodchallengeplaces: List<FoodchallengePlaceModel>) :
    RecyclerView.Adapter<FoodchallengePlaceAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardFoodchallengeplaceBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val foodchallengeplace = foodchallengeplaces[holder.adapterPosition]
        holder.bind(foodchallengeplace)
    }

    override fun getItemCount(): Int = foodchallengeplaces.size

    class MainHolder(private val binding : CardFoodchallengeplaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foodchallengeplace: FoodchallengePlaceModel) {
            binding.foodchallengeplaceTitle.text = foodchallengeplace.title
            binding.restaurant.text = foodchallengeplace.restaurant
            binding.address.text = foodchallengeplace.address
            binding.difficulty.text = foodchallengeplace.difficulty
        }
    }
}