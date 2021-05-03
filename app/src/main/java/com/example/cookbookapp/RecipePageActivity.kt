package com.example.cookbookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import com.example.cookbookapp.database.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecipePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_page)

        val recIntent: Intent = intent
        val recId = recIntent.getIntExtra("recipeId", 0)

        val db = AppDatabase.getInstance(applicationContext)

        val recipeDao = db.recipeDao()

        // Populate views with recipe info
        GlobalScope.launch{
            val recipe = recipeDao.getById(recId)
            findViewById<TextView>(R.id.recipeTitle).text = recipe.name
            findViewById<TextView>(R.id.textViewAuthor).text = recipe.author
            findViewById<TextView>(R.id.textViewRecipeDescription).text = recipe.description
        }
    }
}