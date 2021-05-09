package com.example.cookbookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
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

        GlobalScope.launch{
            if(recipeDao.getAll().isEmpty()){
                runOnUiThread{
                    findViewById<Button>(R.id.btnViewAllRecipes).visibility = Button.GONE
                    findViewById<Button>(R.id.btnRemoveRecipe).visibility = Button.GONE
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
            i.putExtra("mode", "select")
            startActivity(i)
        }

        findViewById<Button>(R.id.btnRemoveRecipe).setOnClickListener{
            val i: Intent = Intent(this, RecipeListActivity::class.java)
            i.putExtra("mode", "remove")
            startActivity(i)
        }


    }
}