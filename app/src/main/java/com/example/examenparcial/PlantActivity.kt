package com.example.examenparcial

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.examenparcial.ui.theme.ExamenParcialTheme
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class PlantActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val plantsDatabase = PlantsDatabase(
            context = this,
            jsonFileString = this
                .resources
                .openRawResource(R.raw.plants)
                .bufferedReader()
                .use { it.readText() })
        val plantId = intent.getStringExtra("plantId")
        val plant: Plant = plantsDatabase.plants.find { it.plantId == plantId }!!

        setContent {
            ExamenParcialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CardViewGUI(plant)
                }
            }
        }
    }
}

@Composable
fun CardViewGUI(plant: Plant) {
    val activity = (LocalContext.current as? Activity)
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxWidth(),
    ) {

        ImageWithIconButtons(
            activity = activity,
            context = context,
            plant = plant
        )

        TitleWithIconButton(
            plant = plant,
            context = context
        )

        PlantDetails(
            plant = plant,
            modifier = Modifier
                .padding(3.dp)
                .align(Alignment.CenterHorizontally)
        )

    }
}

@Composable
//Contenedor imagen, back y share
fun ImageWithIconButtons(activity: Activity?, context: Context, plant: Plant) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart,
    ) {

        //Contenedor de imagen
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = plant.imageUrl,
                contentDescription = "Poster del show",
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }

        //Contenedor de boton back y share
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            //Back
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(all = 8.dp),

                ) {
                IconButton(
                    onClick = { activity?.finish() }
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                }
            }

            //Share
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(all = 8.dp),

                ) {
                IconButton(
                    onClick = {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Check out the ${plant.name} plant in the Android Sunflower app")
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartir",
                    )
                }
            }

        }


    }
}

@Composable
//Nombre de fruta y icono de +
fun TitleWithIconButton(plant: Plant, context: Context) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = plant.name,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )
        }

        Surface(
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
            color = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(all = 8.dp)
        ) {
            IconButton(
                onClick = { AddPlantToGarden(context, plant) }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Favorito")
            }
        }
    }
}

@Composable
//Watering needs y descripcion
fun PlantDetails(plant: Plant, modifier: Modifier) {
    //Necesidades de watering y descripcion
    Text(
        text = "Watering needs",
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = modifier,
    )
    Text(
        text = "every ${plant.wateringInterval} days",
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        modifier = modifier
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = plant.description.replace("<br>","\n").replace(Regex("<.*?>"), ""),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
    }
}

fun AddPlantToGarden(context: Context, plant: Plant) {
    val fecha = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)
    val fechaFormateada = fecha.format(formatter)

    if (File(context.dataDir, "mygarden.json").exists()) {
        var toWrite = readGarden(context)
            .replace("[","")
            .replace("]",",")

        toWrite = toWrite +
                "{\n" +
                "\"plantId\": \"${plant.plantId}\",\n" +
                "\"name\": \"${plant.name}\",\n" +
                "\"description\": \" $fechaFormateada\",\n" +
                "\"growZoneNumber\": ${plant.growZoneNumber},\n" +
                "\"wateringInterval\": ${plant.wateringInterval},\n" +
                "\"imageUrl\": \"${plant.imageUrl}\"\n" +
                "}"
        writeToGarden(context, "[$toWrite]")
    } else {
        writeToGarden(context, "[" +
                "{\n" +
                "\"plantId\": \"${plant.plantId}\",\n" +
                "\"name\": \"${plant.name}\",\n" +
                "\"description\": \"$fechaFormateada\",\n" +
                "\"growZoneNumber\": ${plant.growZoneNumber},\n" +
                "\"wateringInterval\": ${plant.wateringInterval},\n" +
                "\"imageUrl\": \"${plant.imageUrl}\"\n" +
                "}" +
                "]")
    }
}
