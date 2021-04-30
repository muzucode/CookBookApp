package com.example.cookbookapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.cookbookapp.database.AppDatabase
import com.example.cookbookapp.database.recipe.Recipe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.ceil

class RecipeListActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        val db = AppDatabase.getInstance(applicationContext)
        val recipeDao = db.recipeDao()

        // Create first and last recipe indexes
        var firstRecipe: Int = 0
        var lastRecipe: Int
        var currentPage: Int = 1
        lateinit var listRecipes: List<Recipe>

        // Save all recipe textviews to an array list
        var recViewList: ArrayList<TextView> = arrayListOf(
            findViewById<TextView>(R.id.recipe1),
            findViewById<TextView>(R.id.recipe2),
            findViewById<TextView>(R.id.recipe3),
            findViewById<TextView>(R.id.recipe4),
            findViewById<TextView>(R.id.recipe5),
            findViewById<TextView>(R.id.recipe6),
            findViewById<TextView>(R.id.recipe7),
            findViewById<TextView>(R.id.recipe8),
            findViewById<TextView>(R.id.recipe9),
            findViewById<TextView>(R.id.recipe10)
        )


        // Get all recipes in database and load their names
        GlobalScope.launch{
            var count: Int = 0

            // Save all recipes to list
            try{
                // Get all recipes
                listRecipes = recipeDao.getAll()
            }
            catch (e: Exception){

            }

            // Updates page header visibility
            fun updateHeaderVisibility(){
                // BtnFwd visibility
                findViewById<Button>(R.id.btnFwd).visibility = if(listRecipes.size > 10){
                    Button.VISIBLE
                }
                else {
                    Button.INVISIBLE
                }

                // BtnBwd visibility
                findViewById<Button>(R.id.btnBwd).visibility = if(currentPage != 1 ){
                    Button.VISIBLE
                }
                else {
                    Button.INVISIBLE
                }

                // Page number functionality/visibility
                findViewById<TextView>(R.id.textViewPageNumber).text = "$currentPage / ${(listRecipes.size - (10-(listRecipes.size % 10))/10)}"
            }

            // If this list isn't empty then...
            if(listRecipes.isNotEmpty()){

                updateHeaderVisibility()

                lastRecipe = if(listRecipes.size < 10){
                    // If there are less than 10 recipes gotten then lastRecipe index is
                    // last in the array
                    listRecipes.size - 1
                } else {
                    9
                }

                // Set recipe textView names
                for(i in firstRecipe..lastRecipe){
                    recViewList[count].text = listRecipes[i].name
                    count++
                }

                // If all of the textViews weren't updated, then set the remaining ones to invisible
                if(count != 10){
                    for(i in count..9){
                        recViewList[i].visibility = TextView.INVISIBLE
                    }
                }
            }

            // BtnFwd functionality
            findViewById<Button>(R.id.btnFwd).setOnClickListener{
                currentPage += 1
                updateHeaderVisibility()
            }

        }








    }
}