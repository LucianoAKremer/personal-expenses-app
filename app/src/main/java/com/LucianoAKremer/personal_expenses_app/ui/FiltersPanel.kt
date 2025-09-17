package com.LucianoAKremer.personal_expenses_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.LucianoAKremer.personal_expenses_app.data.Category // Importa Category
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersPanel(
    categories: List<Category>, // Cambiado a List<Category>
    onCategorySelected: (Category?) -> Unit, // Cambiado a Category?
    // dateRange: Pair<Date?, Date?>, // Mantener si se usa
    // onDateRangeChanged: (Pair<Date?, Date?>) -> Unit // Mantener si se usa
) {
    var selectedCategoryState by remember { mutableStateOf<Category?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCategoryState?.name ?: "Todas las categorías",
                onValueChange = {},
                label = { Text("Filtrar por Categoría") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Todas las categorías") },
                    onClick = {
                        selectedCategoryState = null
                        onCategorySelected(null)
                        expanded = false
                    }
                )
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            selectedCategoryState = category
                            onCategorySelected(category)
                            expanded = false
                        }
                    )
                }
            }
        }
        // Spacer(modifier = Modifier.height(8.dp))
        // TODO: Implementar filtro por rango de fecha si es necesario
    }
}
    