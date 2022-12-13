package org.wit.foodchallengeplace.views.foodchallengeplacelist

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
import timber.log.Timber.i

class FoodchallengePlaceListView : AppCompatActivity(), FoodchallengePlaceListener {
    lateinit var app: MainApp
    private lateinit var binding: ActivityFoodchallengeListBinding
    lateinit var presenter: FoodchallengePlaceListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodchallengeListBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_foodchallenge_list)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        presenter = FoodchallengePlaceListPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter =
            FoodchallengePlaceAdapter(presenter.getFoodchallengeplaces(), this)

    }

    fun showFoodchallengeplaces(foodchallengeplaces: List<FoodchallengePlaceModel>) {
        binding.recyclerView.adapter = FoodchallengePlaceAdapter(foodchallengeplaces, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        //update the view
        binding.recyclerView.adapter?.notifyDataSetChanged()
        i("recyclerView onResume")
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddFoodchallengeplace() }
            R.id.item_map -> { presenter.doShowFoodchallengeplacesMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFoodchallengeplaceClick(foodchallengeplace: FoodchallengePlaceModel) {
        presenter.doEditFoodchallengeplace(foodchallengeplace)

    }

//    @SuppressLint("NotifyDataSetChanged")
//    private fun loadFoodchallengeplaces() {
//        binding.recyclerView.adapter = FoodchallengePlaceAdapter(presenter.getFoodchallengeplaces(), this)
//        binding.recyclerView.adapter?.notifyDataSetChanged()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        presenter.loadFoodchallengeplaces()
//        super.onActivityResult(requestCode, resultCode, data)
//    }
}