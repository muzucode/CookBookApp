package com.example.cookbookapp.database.recipe

import androidx.room.Dao
import androidx.room.Insert

import androidx.room.Query

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    suspend fun getAll(): List<Recipe>

    @Insert
    suspend fun insertOne(recipe: Recipe)

    @Insert
    suspend fun insertAll(recipes: List<Recipe>)


}