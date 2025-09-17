package com.LucianoAKremer.personal_expenses_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersPanel(
    categories: List<String>,
    onCategorySelected: (String?) -> Unit,
    dateRange: Pair<Date?, Date?>,
    onDateRangeChanged: (Pair<Date?, Date?>) -> Unit
) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        // Filtro por categoría
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = {}
        ) {
            TextField(
                value = selectedCategory ?: "Todas las categorías",
                onValueChange = {},
                label = { Text("Categoría") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            // Aquí se podría implementar el DropdownMenu con categorías
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Filtro por rango de fecha (ejemplo simple)
        Text("Rango de fechas: ${dateRange.first ?: "inicio"} - ${dateRange.second ?: "fin"}")
        // Podés reemplazar por DatePicker real o librería externa
    }
}