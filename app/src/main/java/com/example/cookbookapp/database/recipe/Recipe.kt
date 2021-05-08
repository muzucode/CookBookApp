package com.example.cookbookapp.database.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val rid: Int?,
    val name: String,
    val author: String?,
    val cuisine: String,
    val foodtype: String,
    val ingredients: String,
    val description: String,
    val nativeBookId: Int?,
    val nativeUserId: Int?,
    val logo: String,
    val link: String?
    )