package com.LucianoAKremer.personal_expenses_app.ui // o com.LucianoAKremer.personal_expenses_app.ui.Expenses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlaylistAdd // Asegúrate de importar este específicamente
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.LucianoAKremer.personal_expenses_app.data.Category // Tu clase Category
import com.LucianoAKremer.personal_expenses_app.viewmodel.ExpenseViewModel
// Asegúrate que las siguientes importaciones sean correctas según la ubicación de tus archivos:
import com.LucianoAKremer.personal_expenses_app.ui.AddCategoryDialog
import com.LucianoAKremer.personal_expenses_app.ui.ExpenseItem
import com.LucianoAKremer.personal_expenses_app.ui.Expenses.AddExpenseDialog
import com.LucianoAKremer.personal_expenses_app.ui.Expenses.DeleteExpenseDialog
import com.LucianoAKremer.personal_expenses_app.ui.FiltersPanel
import com.LucianoAKremer.personal_expenses_app.ui.MetricsPanel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(viewModel: ExpenseViewModel) {
    val allExpensesList by viewModel.allExpenses.collectAsState()
    val categoriesList by viewModel.categories.collectAsState()

    var showAddExpenseDialog by remember { mutableStateOf(false) }
    var showAddCategoryDialog by remember { mutableStateOf(false) }
    var showDeleteExpenseDialog by remember { mutableStateOf(false) }
    var showDeleteCategoryDialog by remember { mutableStateOf(false) }
    var selectedExpense by remember { mutableStateOf<com.LucianoAKremer.personal_expenses_app.data.Expense?>(null) }

    // Usar tu clase Category para el estado del filtro
    var selectedFilterCategory by remember { mutableStateOf<Category?>(null) }

    val filteredExpenses = remember(allExpensesList, selectedFilterCategory) {
        if (selectedFilterCategory == null) {
            allExpensesList
        } else {
            allExpensesList.filter { it.categoryId == selectedFilterCategory?.id }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gastos Personales") },
                actions = {
                    IconButton(onClick = { showDeleteCategoryDialog = true }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Borrar Categoría")
                    }
                }
            )
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = { showAddExpenseDialog = true },
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Agregar Gasto")
                }
                FloatingActionButton(onClick = { showAddCategoryDialog = true }) {
                    Icon(
                        Icons.Filled.PlaylistAdd,
                        contentDescription = "Agregar Categoría"
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            FiltersPanel(
                categories = categoriesList,
                onCategorySelected = { category ->
                    selectedFilterCategory = category
                }
            )

            MetricsPanel(expenses = filteredExpenses)
            Spacer(modifier = Modifier.height(8.dp))

            if (filteredExpenses.isEmpty()) {
                Text(
                    text = when {
                        allExpensesList.isEmpty() -> "Aún no tienes gastos registrados."
                        selectedFilterCategory != null -> "No hay gastos para esta categoría."
                        else -> "No hay gastos que coincidan con los filtros."
                    },
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn {
                    items(filteredExpenses) { expense ->
                        val category = categoriesList.find { it.id == expense.categoryId }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            ExpenseItem(expense = expense, category = category, modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                selectedExpense = expense
                                showDeleteExpenseDialog = true
                            }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Borrar Gasto")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddExpenseDialog) {
        AddExpenseDialog(viewModel = viewModel, onDismiss = { showAddExpenseDialog = false })
    }

    if (showAddCategoryDialog) {
        AddCategoryDialog(viewModel = viewModel, onDismiss = { showAddCategoryDialog = false })
    }

    if (showDeleteExpenseDialog && selectedExpense != null) {
        DeleteExpenseDialog(
            viewModel = viewModel,
            onDismiss = {
                showDeleteExpenseDialog = false
                selectedExpense = null
            },
            expenseToDelete = selectedExpense
        )
    }

    if (showDeleteCategoryDialog) {
        DeleteCategoryDialog(viewModel = viewModel, onDismiss = { showDeleteCategoryDialog = false })
    }
}
