package com.LucianoAKremer.personal_expenses_app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.LucianoAKremer.personal_expenses_app.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(viewModel: ExpenseViewModel) {
    val expenses by viewModel.allExpenses.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    // Filtros simples
    var filteredExpenses by remember { mutableStateOf(expenses) }
    val categories = expenses.map { it.category }.distinct()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gastos Personales") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            FiltersPanel(
                categories = categories,
                onCategorySelected = { category ->
                    filteredExpenses = if (category != null) {
                        expenses.filter { it.category == category }
                    } else expenses
                },
                dateRange = Pair(null, null),
                onDateRangeChanged = { /* implementar lógica de filtrado por fechas */ }
            )

            MetricsPanel(filteredExpenses)

            LazyColumn {
                items(filteredExpenses) { expense ->
                    ExpenseItem(expense)
                }
            }
        }
    }

    if (showDialog) {
        AddExpenseDialog(viewModel = viewModel, onDismiss = { showDialog = false })
    }
}
