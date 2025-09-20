package com.LucianoAKremer.personal_expenses_app.ui.Expenses

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
import com.LucianoAKremer.personal_expenses_app.data.Expense
import com.LucianoAKremer.personal_expenses_app.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DeleteExpenseDialog(
    viewModel: ExpenseViewModel,
    onDismiss: () -> Unit,
    expenseToDelete: com.LucianoAKremer.personal_expenses_app.data.Expense?
) {
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    if (expenseToDelete == null) {
        onDismiss()
        return
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Eliminar Gasto") },
        text = {
            Text(
                text = "¿Seguro que deseas eliminar el gasto de ${expenseToDelete.amount} - ${expenseToDelete.note ?: "Sin nota"} - ${dateFormat.format(expenseToDelete.date)}?"
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.deleteExpense(expenseToDelete)
                    onDismiss()
                }
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
