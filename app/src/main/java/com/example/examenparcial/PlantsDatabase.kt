package com.example.examenparcial

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream

class PlantsDatabase(context : Context, jsonFileString: String) {

    val inputStream: InputStream = context.resources.openRawResource(R.raw.plants)
    val jsonFileString = inputStream.bufferedReader().use { it.readText() }

    val plantListType = object: TypeToken<List<Plant>>() {}.type
    val plants: List<Plant> = Gson().fromJson(jsonFileString, plantListType)

}