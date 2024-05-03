package com.example.examenparcial

import android.content.Context
import android.nfc.Tag
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuGUI(plants: List<Plant>) {
    val context = LocalContext.current
    val tabList = listOf("My Garden", "Plant list")
    var tabIndex by remember { mutableStateOf(0) }

    Column {
        // Encabezado
        Text(
            text = "Sunflower",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            fontSize = 25.sp
        )

        // Pestañas
        TabRow(selectedTabIndex = tabIndex) {
            tabList.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }

        // Contenido de las pestañas
        when (tabIndex) {
            0 -> TabContent1(plants, context, tabIndex)
            1 -> TabContent2(plants, context)
        }
    }
}

@Composable
fun TabContent1(plants: List<Plant>, context: Context, tabIndex: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your garden is empty",
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp,
            modifier = Modifier
                .padding(6.dp)
        )
        Button( onClick = { Log.d("DEBUG", "Waos") } )
        {
            Text(text = "Add plant")
        }
    }


}

@Composable
fun TabContent2(plants: List<Plant>, context: Context) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(plants.chunked(2)) { rowItems ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (plant in rowItems) {
                    PlantCard(plant = plant, context = context)
                }
            }
        }
    }
}
