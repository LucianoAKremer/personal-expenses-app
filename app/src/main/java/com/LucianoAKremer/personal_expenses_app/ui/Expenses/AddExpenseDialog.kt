package com.LucianoAKremer.personal_expenses_app.ui.Expenses

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.LucianoAKremer.personal_expenses_app.data.Category
import com.LucianoAKremer.personal_expenses_app.data.Expense
import com.LucianoAKremer.personal_expenses_app.viewmodel.ExpenseViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseDialog(
    viewModel: ExpenseViewModel,        onDismiss: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) } // Cambiado
    var note by remember { mutableStateOf("") }
    val categories by viewModel.categories.collectAsState() // Obtener categorías del ViewModel
    var categoryDropdownExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Gasto") },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Monto") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Selector de Categoría (Dropdown)
                ExposedDropdownMenuBox(
                    expanded = categoryDropdownExpanded,
                    onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedCategory?.name ?: "Seleccionar Categoría",
                        onValueChange = {}, // No editable directamente
                        readOnly = true,
                        label = { Text("Categoría") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = categoryDropdownExpanded,
                        onDismissRequest = { categoryDropdownExpanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    selectedCategory = category
                                    categoryDropdownExpanded = false
                                }
                            )
                        }
                        // Opción para agregar nueva categoría directamente (más avanzado, por ahora no)
                    }
                }
                if (categories.isEmpty()){
                    Text("No hay categorías. Agrega una desde la pantalla principal.", style = MaterialTheme.typography.bodySmall)
                }


                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Nota (opcional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val expenseAmount = amount.toDoubleOrNull()
                    if (expenseAmount != null && selectedCategory != null) {
                        val expense = Expense(
                            amount = expenseAmount,
                            categoryId = selectedCategory!!.id, // Usar el ID de la categoría seleccionada
                            note = note.takeIf { it.isNotBlank() },
                            date = Date()
                        )
                        viewModel.addExpense(expense)
                        onDismiss()
                    } else {
                        // Mostrar error (ej. monto inválido o categoría no seleccionada)
                    }
                },
                // Deshabilitar el botón si no se ha ingresado monto o seleccionado categoría
                enabled = amount.toDoubleOrNull() != null && selectedCategory != null
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
    