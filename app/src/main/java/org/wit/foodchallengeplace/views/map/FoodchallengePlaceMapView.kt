package org.wit.foodchallengeplace.views.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengeplaceMapsBinding
import org.wit.foodchallengeplace.databinding.ContentFoodchallengePlaceMapsBinding
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel

class FoodchallengePlaceMapView : AppCompatActivity() , GoogleMap.OnMarkerClickListener{

    private lateinit var binding: ActivityFoodchallengeplaceMapsBinding
    private lateinit var contentBinding: ContentFoodchallengePlaceMapsBinding
    lateinit var app: MainApp
    lateinit var presenter: FoodchallengePlaceMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivityFoodchallengeplaceMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = FoodchallengePlaceMapPresenter(this)

        contentBinding = ContentFoodchallengePlaceMapsBinding.bind(binding.root)

        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync{ GlobalScope.launch(Dispatchers.Main) {
            presenter.doPopulateMap(it)
        }
        }
    }
    fun showFoodchallengeplaces(foodchallengeplace: FoodchallengePlaceModel) {
        contentBinding.currentTitle.text = foodchallengeplace.title
        contentBinding.currentRestaurant.text = foodchallengeplace.restaurant
        contentBinding.currentDifficulty.text = foodchallengeplace.difficulty
        // contentBinding.currentChallengePicker.text = foodchallengeplace.challengePicker.toString()
        Picasso.get()
            .load(foodchallengeplace.image)
            .into(contentBinding.foodchallengeplaceImage)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        GlobalScope.launch(Dispatchers.Main) {
            presenter.doMarkerSelected(marker)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

}
