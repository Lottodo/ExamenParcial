package com.example.examenparcial

import com.google.gson.Gson

data class Plant(
    val plantId: String,
    val name: String,
    val description: String,
    val growZoneNumber: Int,
    val wateringInterval: Int,
    val imageUrl: String
    ) {

    fun toGson(): String {
        // Crear una instancia de Gson
        val gson = Gson()

        // Convertir el objeto Plant a JSON
        val plantJson = gson.toJson(this)

        return plantJson
    }
}