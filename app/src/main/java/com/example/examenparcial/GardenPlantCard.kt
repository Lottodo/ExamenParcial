package com.example.examenparcial

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


//foto
//nombre
//planted (fecha)
//wattering needs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardenPlantCard(plant: Plant, context: Context) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 16.dp
        ),
        modifier = Modifier
            .size(width = 175.dp, height = 225.dp)
            .padding(12.dp),
        onClick = {
            val intent = Intent(context, PlantActivity::class.java)
            intent.putExtra("plantId", plant.plantId)
            context.startActivity(intent)
        }
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
        ) {

            AsyncImage(
                model = plant.imageUrl,
                contentDescription = "Imagen de la planta",
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Column(
                Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = plant.name,
                    modifier = Modifier
                        .padding(3.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Visible,
                    textAlign = TextAlign.Center,
                    lineHeight = 15.sp
                )
                Text(
                    text = "Planted",
                    modifier = Modifier
                        .padding(0.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Visible,
                    textAlign = TextAlign.Center,
                    lineHeight = 15.sp
                )
                Text(
                    text = plant.description,
                    modifier = Modifier
                        .padding(0.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Visible,
                    textAlign = TextAlign.Center,
                    lineHeight = 15.sp
                )
                Text(
                    text = "Wattering needs",
                    modifier = Modifier
                        .padding(0.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Visible,
                    textAlign = TextAlign.Center,
                    lineHeight = 15.sp
                )
                Text(
                    text = "every ${plant.wateringInterval} days",
                    modifier = Modifier
                        .padding(0.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Visible,
                    textAlign = TextAlign.Center,
                    lineHeight = 15.sp
                )
            }
        }
    }
}
