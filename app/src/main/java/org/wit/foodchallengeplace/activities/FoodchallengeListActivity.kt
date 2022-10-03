package org.wit.foodchallengeplace.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengeListBinding
import org.wit.foodchallengeplace.databinding.CardFoodchallengeplaceBinding
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel

class FoodchallengeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodchallengeListBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodchallengeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = FoodchallengePlaceAdapter(app.foodchallengeplaces)
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