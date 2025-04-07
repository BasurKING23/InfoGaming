package com.example.infogaming.data

import com.angel.InfoGaming.R
import com.google.gson.annotations.SerializedName

class GamesFree (
    val response: String,
    val results: List<Superhero>
)

class game (
    val id: String,
    val title: String,
    val short_description: description,
    val work: Work,
    val appearance: Appearance,
)