package com.example.examenparcial

import android.content.Context
import android.widget.Toast
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun readGarden(context: Context): String {
    val data = StringBuffer()
    val f = File(context.dataDir, "mygarden.json")
    if (!f.exists()) {
        Toast.makeText(context,"File not found", Toast.LENGTH_SHORT).show()
    } else {
        val fileReader = BufferedReader(FileInputStream(f).bufferedReader())
        var line = fileReader.readLine()
        while(line != null) {
            data.append(line)
            line = fileReader.readLine()
        }
        fileReader.close()
    }

    return data.toString()
}

fun writeToGarden(context: Context, content: String) {
    val f = File(context.dataDir,"mygarden.json")
    if (!f.exists())
        f.createNewFile()
    //} else {
        val fileWriter = BufferedWriter(FileOutputStream(f).bufferedWriter())
        fileWriter.write(content)
        fileWriter.close()
    //}
}

