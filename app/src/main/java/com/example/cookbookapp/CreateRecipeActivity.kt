package com.example.cookbookapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.cookbookapp.database.AppDatabase
import com.example.cookbookapp.database.recipe.ActiveEnv
import com.example.cookbookapp.database.recipe.Recipe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateRecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipe)

        val db = AppDatabase.getInstance(applicationContext)

        val recipeDao = db.recipeDao()

        // Button intent to publish recipe
        findViewById<Button>(R.id.btnPublish).setOnClickListener {
            GlobalScope.launch {

                // Get all the inputted info
                val name: String = findViewById<EditText>(R.id.editTextRecipeName).text.toString()
                val cuisine: String = findViewById<EditText>(R.id.editTextCuisine).text.toString()
                val description: String = findViewById<EditText>(R.id.editTextSteps).text.toString()
                if(name.isNotBlank() && cuisine.isNotBlank() && description.isNotBlank()){
                    recipeDao.insertOne(
                        Recipe(
                            null,
                            name,
                            "Muzucode",
                            cuisine,
                            "Soup",
                            "potatoes",
                            description,
                            77,
                            ActiveEnv.user,
                            "potatoes"
                        )
                    )

                    runOnUiThread{
                        findViewById<EditText>(R.id.editTextRecipeName).setText("")
                        findViewById<EditText>(R.id.editTextCuisine).setText("")
                        findViewById<EditText>(R.id.editTextSteps).setText("")
                    }
                }
                else {
                    println("A field is blank")
                }
            }
        }


        // Button intent to go back to all recipes
        findViewById<Button>(R.id.btnAllRecipes).setOnClickListener{
            val i: Intent = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}