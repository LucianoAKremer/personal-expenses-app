package com.LucianoAKremer.personal_expenses_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.LucianoAKremer.personal_expenses_app.data.Expense

@Composable
fun MetricsPanel(expenses: List<Expense>) {
    val total = expenses.sumOf { it.amount }
    val count = expenses.size
    val average = if (count > 0) total / count else 0.0

    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        Card(modifier = Modifier.weight(1f).padding(4.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Total gastado")
                Text("$${"%.2f".format(total)}")
            }
        }
        Card(modifier = Modifier.weight(1f).padding(4.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Cantidad de gastos")
                Text("$count")
            }
        }
        Card(modifier = Modifier.weight(1f).padding(4.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Promedio por gasto")
                Text("$${"%.2f".format(average)}")
            }
        }
    }
}