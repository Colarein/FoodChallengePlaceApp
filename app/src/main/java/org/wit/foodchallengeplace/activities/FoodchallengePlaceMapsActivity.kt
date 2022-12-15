//package org.wit.foodchallengeplace.activities
//
//import android.os.Bundle
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.Marker
//import com.google.android.gms.maps.model.MarkerOptions
//import com.squareup.picasso.Picasso
//import org.wit.foodchallengeplace.R
//import org.wit.foodchallengeplace.databinding.ActivityFoodchallengeplaceMapsBinding
//import org.wit.foodchallengeplace.databinding.ContentFoodchallengePlaceMapsBinding
//import org.wit.foodchallengeplace.main.MainApp
//
//class FoodchallengePlaceMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {
//
//    private lateinit var binding: ActivityFoodchallengeplaceMapsBinding
//    private lateinit var contentBinding: ContentFoodchallengePlaceMapsBinding
//    lateinit var map: GoogleMap
//    lateinit var app: MainApp
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        app = application as MainApp
//        binding = ActivityFoodchallengeplaceMapsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setSupportActionBar(binding.toolbar)
//
//        contentBinding = ContentFoodchallengePlaceMapsBinding.bind(binding.root)
//        contentBinding.mapView.onCreate(savedInstanceState)
//        contentBinding.mapView.getMapAsync {
//            map = it
//            configureMap()
//        }
//    }
//
//    suspend fun configureMap() {
//        map.setOnMarkerClickListener(this)
//        map.uiSettings.isZoomControlsEnabled = true
//
//        app.foodchallengeplaces.findAll().forEach {
//            val loc = LatLng(it.lat, it.lng)
//            val options = MarkerOptions().title(it.title).position(loc)
//            map.addMarker(options)?.tag = it.id
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
//        }
//    }
//
//    override suspend fun onMarkerClick(marker: Marker): Boolean {
//        val tag = marker.tag as Long
//        val foodchallengeplace = app.foodchallengeplaces.findById(tag)
//        if (foodchallengeplace != null) {
//            contentBinding.currentTitle.text = foodchallengeplace.title
//        }
//        if (foodchallengeplace != null) {
//            contentBinding.currentRestaurant.text = foodchallengeplace.restaurant
//        }
//        if (foodchallengeplace != null) {
//            contentBinding.currentDifficulty.text = foodchallengeplace.difficulty
//        }
//
////        if (foodchallengeplace != null) {
////            contentBinding.currentChallengePicker.text = foodchallengeplace.challengePicker.toString()
////        }
//        if (foodchallengeplace != null) {
//            Picasso.get()
//                .load(foodchallengeplace.image)
//                .into(contentBinding.foodchallengeplaceImage)
//        }
//        return true
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        contentBinding.mapView.onDestroy()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        contentBinding.mapView.onLowMemory()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        contentBinding.mapView.onPause()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        contentBinding.mapView.onResume()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        contentBinding.mapView.onSaveInstanceState(outState)
//    }
//}
