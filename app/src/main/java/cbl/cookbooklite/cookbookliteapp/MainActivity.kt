package cbl.cookbooklite.cookbookliteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cookbookapp.R
import cbl.cookbooklite.cookbookliteapp.database.AppDatabase
import cbl.cookbooklite.cookbookliteapp.fragments.NoRecipesFoundFragment
import cbl.cookbooklite.cookbookliteapp.fragments.RecipePageFragment
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
                    findViewById<Button>(R.id.btnViewAllRecipes).apply{
                        setBackgroundColor(resources.getColor(R.color.design_default_color_error))
                        text = "Browse (No recipes found)"
                        isClickable = false
                    }

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