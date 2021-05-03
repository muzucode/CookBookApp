package com.example.cookbookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.cookbookapp.database.AppDatabase
import com.example.cookbookapp.fragments.NoRecipesFoundFragment
import com.example.cookbookapp.fragments.RecipePageFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDatabase.getInstance(applicationContext)

        val recipeDao = db.recipeDao()


        val recipeFragment = RecipePageFragment()
        val noRecipesFoundFragment = NoRecipesFoundFragment()

        // Set button event
        findViewById<Button>(R.id.btnRandom).setOnClickListener {

            // If no recipes in DB then show no recipes found
            GlobalScope.launch{
                val allRecipes = recipeDao.getAll()
                if(allRecipes.isEmpty()){
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.containerFragment, noRecipesFoundFragment )
                        commit()
                    }
                }
                else {
                    // Show recipe page
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.containerFragment, recipeFragment)
                        commit()
                    }
                    val rand = (0..allRecipes.size - 1).random()
                    val chosenRecipe = allRecipes[rand]
                    runOnUiThread {
                        // Set food icon image
                        when (chosenRecipe.foodtype) {
                            "Soup" -> findViewById<ImageView>(R.id.recipeImg).setImageResource(R.drawable.souplogo)
                            "Sushi" -> findViewById<ImageView>(R.id.recipeImg).setImageResource(R.drawable.sushilogo)
                            else -> { // Note the block
                                print("Chosen recipe has no type or wrong type")
                            }
                        }
                        findViewById<TextView>(R.id.recipeTitle).text = chosenRecipe.name
                        findViewById<TextView>(R.id.textViewAuthor).text = "By: ${chosenRecipe.author}"
                        findViewById<TextView>(R.id.textViewRecipeDescription).text = chosenRecipe.description
                        println(chosenRecipe.description)
                    }

                }
            }
        }


        // Create recipe fragment
        findViewById<Button>(R.id.btnCreate).setOnClickListener{
            val i: Intent = Intent(this, CreateRecipeActivity::class.java)
            startActivity(i)
        }

        findViewById<Button>(R.id.btnViewAllRecipes).setOnClickListener{
            val i: Intent = Intent(this, RecipeListActivity::class.java)
            startActivity(i)
        }
    }
}