package org.wit.foodchallengeplace.views.foodchallengeplacelist
import android.annotation.SuppressLint
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

class FoodchallengePlaceListView : AppCompatActivity(), FoodchallengePlaceListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFoodchallengeListBinding
    lateinit var presenter: FoodchallengePlaceListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodchallengeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = FoodchallengePlaceListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadFoodchallengeplaces()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
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

    @SuppressLint("NotifyDataSetChanged")
    private fun loadFoodchallengeplaces() {
        binding.recyclerView.adapter = FoodchallengePlaceAdapter(presenter.getFoodchallengeplaces(), this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}