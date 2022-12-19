package org.wit.foodchallengeplace.room

import android.content.Context
import androidx.room.Room
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel
import org.wit.foodchallengeplace.models.FoodchallengePlaceStore

class FoodchallengePlaceStoreRoom( val context: Context) : FoodchallengePlaceStore {

    var dao: FoodchallengePlaceDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.foodchallengeplaceDao()
    }

    override suspend fun findAll(): List<FoodchallengePlaceModel> {
        return dao.findAll()
    }

    override suspend fun findById(id: Long): FoodchallengePlaceModel? {
        return dao.findById(id)
    }

    override suspend fun create(foodchallengeplace: FoodchallengePlaceModel) {
        dao.create(foodchallengeplace)
    }

    override suspend fun update(foodchallengeplace: FoodchallengePlaceModel) {
        dao.update(foodchallengeplace)
    }

    override suspend fun delete(foodchallengeplace: FoodchallengePlaceModel) {
        dao.deleteFoodchallengeplace(foodchallengeplace)
    }

    override suspend fun clear(){
    }
}