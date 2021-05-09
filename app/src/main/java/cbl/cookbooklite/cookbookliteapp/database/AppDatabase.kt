package cbl.cookbooklite.cookbookliteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import cbl.cookbooklite.cookbookliteapp.database.recipe.Recipe
import cbl.cookbooklite.cookbookliteapp.database.recipe.RecipeDao

@Database(entities = [Recipe::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = databaseBuilder(context, AppDatabase::class.java, "firstdb").build()
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}
