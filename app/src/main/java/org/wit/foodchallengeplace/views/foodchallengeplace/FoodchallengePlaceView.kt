package org.wit.foodchallengeplace.views.foodchallengeplace

// import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.wit.foodchallengeplace.R
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengesplaceBinding
import org.wit.foodchallengeplace.models.Location
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import timber.log.Timber.i

class FoodchallengePlaceView : AppCompatActivity() {

    private lateinit var binding: ActivityFoodchallengesplaceBinding
    private lateinit var presenter: FoodchallengePlacePresenter
    lateinit var map: GoogleMap
    var foodchallengeplace = FoodchallengePlaceModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodchallengesplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.foodchallengeToolbarAdd.title = title
        setSupportActionBar(binding.foodchallengeToolbarAdd)

        presenter = FoodchallengePlacePresenter(this)

        binding.chooseImage.setOnClickListener {
            presenter.cacheFoodchallengeplace(binding.foodchallengeplaceTitle.text.toString(), binding.restaurant.text.toString(), binding.difficulty.text.toString())
            presenter.doSelectImage()
        }

        binding.mapView2.setOnClickListener {
            presenter.cacheFoodchallengeplace(binding.foodchallengeplaceTitle.text.toString(), binding.restaurant.text.toString(), binding.difficulty.text.toString())
            presenter.doSetLocation()
        }

        binding.mapView2.onCreate(savedInstanceState)
        binding.mapView2.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_foodchallengeplace, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        if (presenter.edit){
            deleteMenu.setVisible(true)
        }
        else{
            deleteMenu.setVisible(false)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                if (binding.foodchallengeplaceTitle.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_foodchallengeplace_title, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    GlobalScope.launch(Dispatchers.IO) {
                        presenter.doAddOrSave(
                            binding.foodchallengeplaceTitle.text.toString(),
                            binding.restaurant.text.toString(),
                            binding.difficulty.text.toString()
                        )
                    }
                }
            }
            R.id.item_delete -> {
                GlobalScope.launch(Dispatchers.IO){
                    presenter.doDelete()
                }
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun showFoodchallengeplace(foodchallengeplace: FoodchallengePlaceModel) {
        if (binding.foodchallengeplaceTitle.text.isEmpty()) binding.foodchallengeplaceTitle.setText(foodchallengeplace.title)
        if (binding.restaurant.text.isEmpty()) binding.restaurant.setText(foodchallengeplace.restaurant)
        if(binding.difficulty.text.isEmpty()) binding.difficulty.setText(foodchallengeplace.difficulty)

        if (foodchallengeplace.image != "") {
            Picasso.get()
                .load(foodchallengeplace.image)
                .into(binding.foodchallengeplaceImage)

            binding.chooseImage.setText(R.string.change_foodchallengeplace_image)
        }
        binding.lat.setText("%.6f".format(foodchallengeplace.lat))
        binding.lng.setText("%.6f".format(foodchallengeplace.lng))

    }

    fun updateImage(image: String){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.foodchallengeplaceImage)
        binding.chooseImage.setText(R.string.change_foodchallengeplace_image)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView2.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView2.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView2.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView2.onResume()
        presenter.doRestartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView2.onSaveInstanceState(outState)
    }

}