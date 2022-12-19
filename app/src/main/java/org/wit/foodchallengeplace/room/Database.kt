package org.wit.foodchallengeplace.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.wit.foodchallengeplace.helpers.Converters
import org.wit.foodchallengeplace.models.FoodchallengePlaceModel

@Database(entities = [FoodchallengePlaceModel::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun foodchallengeplaceDao(): FoodchallengePlaceDao
}