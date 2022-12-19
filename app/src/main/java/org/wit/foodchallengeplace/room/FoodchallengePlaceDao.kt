package org.wit.foodchallengeplace.room

import androidx.room.*
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel

@Dao
interface FoodchallengePlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(foodchallengeplace: FoodchallengePlaceModel)

    @Query("SELECT * FROM FoodchallengePlaceModel")
    suspend fun findAll(): List<FoodchallengePlaceModel>

    @Query("select * from FoodchallengePlaceModel where fbId = :fbId")
    suspend fun findById(fbId: String): FoodchallengePlaceModel

    @Update
    suspend fun update(foodchallengeplace: FoodchallengePlaceModel)

    @Delete
    suspend  fun deleteFoodchallengeplace(foodchallengeplace: FoodchallengePlaceModel)
}