package org.wit.foodchallengeplace.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.foodchallengeplace.databinding.CardFoodchallengeplaceBinding
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel

interface FoodchallengePlaceListener {
    fun onFoodchallengeplaceClick(foodchallengeplace: FoodchallengePlaceModel)
}

class FoodchallengePlaceAdapter constructor(private var foodchallengeplaces: List<FoodchallengePlaceModel>,
                                            private val listener: FoodchallengePlaceListener):
    RecyclerView.Adapter<FoodchallengePlaceAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardFoodchallengeplaceBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val foodchallengeplace = foodchallengeplaces[holder.adapterPosition]
        holder.bind(foodchallengeplace, listener)
    }

    override fun getItemCount(): Int = foodchallengeplaces.size

    class MainHolder(private val binding : CardFoodchallengeplaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(foodchallengeplace: FoodchallengePlaceModel, listener:FoodchallengePlaceListener) {
            binding.foodchallengeplaceTitle.text = foodchallengeplace.title
            binding.restaurant.text = foodchallengeplace.restaurant
            binding.address.text = foodchallengeplace.address
            binding.difficulty.text = foodchallengeplace.difficulty
            binding.challengePicker.text = foodchallengeplace.challengePicker.toString()
            Picasso.get().load(foodchallengeplace.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onFoodchallengeplaceClick(foodchallengeplace) }
        }
    }
}