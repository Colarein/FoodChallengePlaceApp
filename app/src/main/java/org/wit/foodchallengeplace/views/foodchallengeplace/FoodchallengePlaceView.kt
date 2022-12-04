package org.wit.foodchallengeplace.views.foodchallengeplace

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.foodchallengeplace.R
import org.wit.foodchallengeplace.databinding.ActivityFoodchallengesplaceBinding
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import timber.log.Timber.i

class FoodchallengePlaceView : AppCompatActivity() {
    private lateinit var binding: ActivityFoodchallengesplaceBinding
    private lateinit var presenter: FoodchallengePlacePresenter
    var foodchallengeplace = FoodchallengePlaceModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityFoodchallengesplaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.foodchallengetoolbarAdd.title = title
        setSupportActionBar(binding.foodchallengetoolbarAdd)

        presenter = FoodchallengePlacePresenter(this)
        binding.btnAdd.setOnClickListener {
            foodchallengeplace.title = binding.foodchallengeplaceTitle.text.toString()
            foodchallengeplace.restaurant = binding.restaurant.text.toString()
            foodchallengeplace.address = binding.address.text.toString()
            foodchallengeplace.difficulty = binding.difficulty.text.toString()

            if (foodchallengeplace.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_foodchallengeplace_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                presenter.doAddOrSave(
                    foodchallengeplace.title,
                    foodchallengeplace.restaurant,
                    foodchallengeplace.address,
                    foodchallengeplace.difficulty,
                    foodchallengeplace.challengePicker)

            }
            i("add Button Pressed: $foodchallengeplace")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            presenter.doSelectImage()
        }

        binding.foodchallengeplaceLocation.setOnClickListener {
            presenter.doSetLocation()
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
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showFoodchallengeplaces(foodchallengeplace: FoodchallengePlaceModel) {
        binding.foodchallengeplaceTitle.setText(foodchallengeplace.title)
        binding.restaurant.setText(foodchallengeplace.restaurant)
        binding.address.setText(foodchallengeplace.address)
        binding.difficulty.setText(foodchallengeplace.difficulty)
        // binding.challengePicker.setText(foodchallengeplace.challengePicker)

        Picasso.get()
            .load(foodchallengeplace.image)
            .into(binding.foodchallengeplaceImage)
        if (foodchallengeplace.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_foodchallengeplace_image)
        }

    }

    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.foodchallengeplaceImage)
        binding.chooseImage.setText(R.string.change_foodchallengeplace_image)
    }
}