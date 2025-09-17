package com.LucianoAKremer.personal_expenses_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.LucianoAKremer.personal_expenses_app.data.Expenses.Expense
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = expense.category, style = MaterialTheme.typography.titleMedium)
            Text(text = "$${expense.amount}", style = MaterialTheme.typography.bodyLarge)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            Text(text = dateFormat.format(expense.date), style = MaterialTheme.typography.bodySmall)
            expense.note?.let {
                Text(text = it, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
