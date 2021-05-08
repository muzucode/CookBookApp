package com.example.cookbookapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
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

            // Set thumbnail based on recipe name
            if(recipe.name.contains("Soup")){
                findViewById<ImageView>(R.id.recipeImg).setImageResource(R.drawable.souplogo)
            }
            if(recipe.name.contains("Sushi")){
                findViewById<ImageView>(R.id.recipeImg).setImageResource(R.drawable.sushilogo)
            }

            // Get video link from recipe
            val videoURL = recipe.link
            // Set video link textview onclick intent
            findViewById<TextView>(R.id.textViewVideoLink).setOnClickListener{
                val i: Intent = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(videoURL)
                startActivity(i)
            }
        }
    }
}