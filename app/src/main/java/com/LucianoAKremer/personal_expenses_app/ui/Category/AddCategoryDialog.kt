package com.LucianoAKremer.personal_expenses_app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.LucianoAKremer.personal_expenses_app.viewmodel.ExpenseViewModel

@Composable
fun AddCategoryDialog(
    viewModel: ExpenseViewModel,
    onDismiss: () -> Unit
) {
    var categoryName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Nueva Categoría") },
        text = {
            Column {
                OutlinedTextField(value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text("Nombre de la Categoría") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (categoryName.isNotBlank()) {
                        viewModel.addCategory(categoryName)
                        onDismiss()
                    }
                },
                enabled = categoryName.isNotBlank()
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
    