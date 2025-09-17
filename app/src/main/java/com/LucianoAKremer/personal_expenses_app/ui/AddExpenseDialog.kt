package com.LucianoAKremer.personal_expenses_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.LucianoAKremer.personal_expenses_app.data.Expenses.Expense
import com.LucianoAKremer.personal_expenses_app.viewmodel.ExpenseViewModel
import java.util.Date

@Composable
fun AddExpenseDialog(
    viewModel: ExpenseViewModel,
    onDismiss: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Gasto") },
        text = {
            Column {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Monto") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Categoría") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Nota (opcional)") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val expense = Expense(
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    category = category,
                    note = note,
                    date = Date()
                )
                viewModel.addExpense(expense)
                onDismiss()
            }) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}