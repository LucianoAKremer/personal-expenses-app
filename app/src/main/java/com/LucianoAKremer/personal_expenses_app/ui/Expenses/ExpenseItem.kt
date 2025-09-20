package com.LucianoAKremer.personal_expenses_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.LucianoAKremer.personal_expenses_app.data.Category // Importa Category
import com.LucianoAKremer.personal_expenses_app.data.Expense
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ExpenseItem(expense: Expense, category: Category?, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp), // Ajusta padding
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Sutil elevación
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category?.name ?: "Sin Categoría", // Muestra el nombre de la categoría
                    style = MaterialTheme.typography.titleMedium
                )
                expense.note?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                Text(
                    text = "$${"%.2f".format(expense.amount)}", // Formatea el monto
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                val dateFormat = SimpleDateFormat("dd/MM/yy", Locale.getDefault()) // Formato más corto
                Text(                        text = dateFormat.format(expense.date),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
