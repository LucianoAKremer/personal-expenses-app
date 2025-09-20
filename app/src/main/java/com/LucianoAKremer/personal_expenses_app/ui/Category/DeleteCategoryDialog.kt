package com.LucianoAKremer.personal_expenses_app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.LucianoAKremer.personal_expenses_app.data.Category
import com.LucianoAKremer.personal_expenses_app.viewmodel.ExpenseViewModel

@Composable
fun DeleteCategoryDialog(
    viewModel: ExpenseViewModel,
    onDismiss: () -> Unit
) {
    val categories by viewModel.categories.collectAsState()
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Eliminar Categoría") },
        text = {
            Column {
                if (categories.isEmpty()) {
                    Text("No hay categorías para eliminar.")
                } else {
                    categories.forEach { category ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedCategory == category,
                                    onClick = { selectedCategory = category }
                                )
                                .background(
                                    if (selectedCategory == category) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent
                                )
                                .padding(8.dp)
                        ) {
                            RadioButton(
                                selected = selectedCategory == category,
                                onClick = { selectedCategory = category }
                            )
                            Text(
                                text = category.name,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    selectedCategory?.let {
                        viewModel.deleteCategory(it)
                        onDismiss()
                    }
                },
                enabled = selectedCategory != null
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
