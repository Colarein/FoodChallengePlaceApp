package org.wit.foodchallengeplace.views.foodchallengeplacelist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import org.wit.foodchallengeplace.R
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengeListBinding
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import timber.log.Timber.i

class FoodchallengePlaceListView : AppCompatActivity(), FoodchallengePlaceListener {

    lateinit var app: MainApp
    lateinit var binding: ActivityFoodchallengeListBinding
    lateinit var presenter: FoodchallengePlaceListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFoodchallengeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //update Toolbar title
        binding.toolbar.title = title
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            binding.toolbar.title = "${title}: ${user.email}"
        }
        setSupportActionBar(binding.toolbar)

        presenter = FoodchallengePlaceListPresenter(this)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        updateRecyclerView()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {

        //update the view
        super.onResume()
        updateRecyclerView()
        binding.recyclerView.adapter?.notifyDataSetChanged()
        i("recyclerView onResume")

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddFoodchallengeplace() }
            R.id.item_map -> { presenter.doShowFoodchallengeplacesMap() }
            R.id.item_logout -> {
                GlobalScope.launch(Dispatchers.IO) {
                    presenter.doLogout()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFoodchallengeplaceClick(foodchallengeplace: FoodchallengePlaceModel) {
        presenter.doEditFoodchallengeplace(foodchallengeplace)

    }

    private fun updateRecyclerView(){
        GlobalScope.launch(Dispatchers.Main){
            binding.recyclerView.adapter =
                FoodchallengePlaceAdapter(presenter.getFoodchallengeplaces(), this@FoodchallengePlaceListView)
        }
    }

}