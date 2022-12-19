@file:Suppress("DEPRECATION")

package org.wit.foodchallengeplace.views.foodchallengeplace

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.foodchallengeplace.helpers.checkLocationPermissions
import org.wit.foodchallengeplace.helpers.createDefaultLocationRequest
import org.wit.foodchallengeplace.helpers.showImagePicker
import org.wit.foodchallengeplace.main.MainApp
import org.wit.foodchallengeplace.models.Location
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import org.wit.foodchallengeplace.views.location.EditLocationView
import timber.log.Timber.i

class FoodchallengePlacePresenter(private val view: FoodchallengePlaceView) {
    private val locationRequest = createDefaultLocationRequest()
    var map: GoogleMap? = null
    var foodchallengeplace = FoodchallengePlaceModel()
    var app: MainApp = view.application as MainApp
    var locationManuallyChanged = false
    //location service
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    var edit = false
    private val location = Location(51.8985, -8.4756, 15f)

    init {

        doPermissionLauncher()
        registerImagePickerCallback()
        registerMapCallback()

        if (view.intent.hasExtra("foodchallengeplace_edit")) {
            edit = true
            foodchallengeplace = view.intent.extras?.getParcelable("foodchallengeplace_edit")!!
            view.showFoodchallengeplace(foodchallengeplace)
        }
        else {

            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
            foodchallengeplace.lat = location.lat
           foodchallengeplace.lng = location.lng
        }

    }


    suspend fun doAddOrSave(title: String, restaurant: String, difficulty: String) {
        foodchallengeplace.title = title
        foodchallengeplace.restaurant = restaurant
        foodchallengeplace.difficulty = difficulty
//        foodchallengeplace.challengePicker = challengePicker
        if (edit) {
            app.foodchallengeplaces.update(foodchallengeplace)
        } else {
            app.foodchallengeplaces.create(foodchallengeplace)
        }

        view.finish()

    }

    fun doCancel() {
        view.finish()

    }

    suspend fun doDelete() {
        app.foodchallengeplaces.delete(foodchallengeplace)
        view.finish()

    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher)
    }

    fun doSetLocation() {
        locationManuallyChanged = true

        if (foodchallengeplace.zoom != 0f) {

            location.lat =  foodchallengeplace.lat
            location.lng = foodchallengeplace.lng
            location.zoom = foodchallengeplace.zoom
            locationUpdate(foodchallengeplace.lat, foodchallengeplace.lng)
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {

        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }

    @SuppressLint("MissingPermission")
    fun doRestartLocationUpdates() {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val l = locationResult.locations.last()
                if(!locationManuallyChanged){
                    locationUpdate(l.latitude, l.longitude)
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }
    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(foodchallengeplace.lat, foodchallengeplace.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        foodchallengeplace.lat = lat
        foodchallengeplace.lng = lng
        foodchallengeplace.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(foodchallengeplace.title).position(LatLng(foodchallengeplace.lat, foodchallengeplace.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(foodchallengeplace.lat, foodchallengeplace.lng), foodchallengeplace.zoom))
        view.showFoodchallengeplace(foodchallengeplace)
    }

    fun cacheFoodchallengeplace (title: String, restaurant: String, difficulty: String) {
        foodchallengeplace.title = title
        foodchallengeplace.restaurant = restaurant
        foodchallengeplace.difficulty = difficulty
    }

    private fun registerImagePickerCallback() {

        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            foodchallengeplace.image = result.data!!.data!!.toString()
                            view.updateImage(foodchallengeplace.image)
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            foodchallengeplace.lat = location.lat
                            foodchallengeplace.lng = location.lng
                            foodchallengeplace.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }

    private fun doPermissionLauncher() {
        i("permission check called")
        requestPermissionLauncher =
            view.registerForActivityResult(ActivityResultContracts.RequestPermission())
            { isGranted: Boolean ->
                if (isGranted) {
                    doSetCurrentLocation()
                } else {
                    locationUpdate(location.lat, location.lng)
                }
            }
    }
}