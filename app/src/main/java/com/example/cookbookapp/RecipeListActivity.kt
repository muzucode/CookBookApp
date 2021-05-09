package com.example.cookbookapp

import android.annotation.SuppressLint
import android.content.Intent
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

        // Get the mode that is passed over
        var pageMode: String? = intent.getStringExtra("mode")

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
                findViewById<Button>(R.id.btnFwd).visibility = if(listRecipes.size > currentPage*10){
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
                findViewById<TextView>(R.id.textViewPageNumber).text = "$currentPage / ${(ceil(listRecipes.size / 10.0).toInt())}"
            }

            // Updates recipe text view content and visibility
            fun updateTextViews(){
                var count: Int = 0
                // If there are less than 10 recipes gotten then lastRecipe index is
                // last in the array

                lastRecipe = if(listRecipes.size < currentPage * 10){

                    listRecipes.size - (currentPage-1) * 10 - 1
                } else {
                    9
                }

                // Set recipe textView names and click intents
                for(i in firstRecipe..lastRecipe){
                    recViewList[count].text = listRecipes[i + 10*(currentPage-1)].name

                    // Set click listener intent to go to the recipe page
                    recViewList[count].setOnClickListener{
                        if(pageMode == "select"){
                            val intent: Intent = Intent(this@RecipeListActivity, RecipePageActivity::class.java)
                            intent.putExtra("recipeId", listRecipes[i + 10*(currentPage-1)].rid)
                            startActivity(intent)
                        }
                        // If in removal mode, then delete the recipe
                        if(pageMode == "remove"){
                            GlobalScope.launch{
                                recipeDao.deleteOne(listRecipes[i + 10*(currentPage-1)])
                            }
                            val intent: Intent = Intent(this@RecipeListActivity, MainActivity::class.java)
                            startActivity(intent)
                        }

                    }
                    count++
                }

                // If all of the textViews weren't updated, then set the remaining ones to invisible
                if(count != 10){
                    for(i in count..9){
                        recViewList[i].visibility = TextView.INVISIBLE
                    }
                }
                // Else, set all to visible
                else {
                    for(i in 0..9){
                        recViewList[i].visibility = TextView.VISIBLE
                    }
                }
            }

            // Initial UI update onCreate
            if(listRecipes.isNotEmpty()){

                updateHeaderVisibility()
                updateTextViews()

            }

            // BtnFwd functionality
            findViewById<Button>(R.id.btnFwd).setOnClickListener{
                currentPage++
                updateHeaderVisibility()
                updateTextViews()
            }

            // BtnBwd functionality
            findViewById<Button>(R.id.btnBwd).setOnClickListener{
                currentPage--
                updateHeaderVisibility()
                updateTextViews()
            }

        }
    }
}