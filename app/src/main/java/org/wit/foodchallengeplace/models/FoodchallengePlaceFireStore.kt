package org.wit.foodchallengeplace.models

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import org.wit.foodchallengeplace.helpers.readImageFromPath
import timber.log.Timber.i
import java.io.ByteArrayOutputStream
import java.io.File

class FoodchallengePlaceFireStore(val context: Context) : FoodchallengePlaceStore {
    val foodchallengeplaces = ArrayList<FoodchallengePlaceModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override suspend fun findAll(): List<FoodchallengePlaceModel> {
        return foodchallengeplaces
    }


    override suspend fun findById(id: Long): FoodchallengePlaceModel? {
        val foundFoodchallengeplace: FoodchallengePlaceModel? = foodchallengeplaces.find { p -> p.id == id }
        return foundFoodchallengeplace
    }

    override suspend fun create(foodchallengeplace: FoodchallengePlaceModel) {
        val key = db.child("users").child(userId).child("foodchallengeplaces").push().key
        key?.let {
            foodchallengeplace.fbId = key
            foodchallengeplaces.add(foodchallengeplace)
            db.child("users").child(userId).child("foodchallengeplaces").child(key).setValue(foodchallengeplace)
            updateImage(foodchallengeplace)
        }
    }

    override suspend fun update(foodchallengeplace: FoodchallengePlaceModel) {
        var foundFoodchallengeplace: FoodchallengePlaceModel? = foodchallengeplaces.find{ p -> p.fbId == foodchallengeplace.fbId }
        if (foundFoodchallengeplace != null) {
            foundFoodchallengeplace.title = foodchallengeplace.title
            foundFoodchallengeplace.restaurant = foodchallengeplace.restaurant
            foundFoodchallengeplace.difficulty = foodchallengeplace.difficulty
//            foundFoodchallengeplace.challengePicker = foodchallengeplace.challengePicker
            foundFoodchallengeplace.image = foodchallengeplace.image
            foundFoodchallengeplace.location = foodchallengeplace.location
        }

        db.child("users").child(userId).child("foodchallengeplaces").child(foodchallengeplace.fbId).setValue(foodchallengeplace)
        if(foodchallengeplace.image.length > 0){
            updateImage(foodchallengeplace)
        }
    }


    override suspend fun delete(foodchallengeplace: FoodchallengePlaceModel) {
        db.child("users").child(userId).child("foodchallengeplaces").child(foodchallengeplace.fbId).removeValue()
        foodchallengeplaces.remove(foodchallengeplace)
    }

    override suspend fun clear() {
        foodchallengeplaces.clear()
    }

    fun fetchFoodchallengeplaces(foodchallengeplacesReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(foodchallengeplaces) {
                    it.getValue<FoodchallengePlaceModel>(
                        FoodchallengePlaceModel::class.java
                    )
                }
                foodchallengeplacesReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        st = FirebaseStorage.getInstance().reference
        db = FirebaseDatabase.getInstance("https://foodchallenge-app-default-rtdb.firebaseio.com/").reference
        foodchallengeplaces.clear()
        db.child("users").child(userId).child("foodchallengeplaces")
            .addListenerForSingleValueEvent(valueEventListener)
    }

    fun updateImage(foodchallengeplace: FoodchallengePlaceModel) {
        if (foodchallengeplace.image != "") {
            val fileName = File(foodchallengeplace.image)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, foodchallengeplace.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        foodchallengeplace.image = it.toString()
                        db.child("users").child(userId).child("foodchallengeplaces").child(foodchallengeplace.fbId).setValue(foodchallengeplace)
                    }
                }.addOnFailureListener{
                    var errorMessage = it.message
                    i("Failure: $errorMessage")
                }
            }

        }
    }
}