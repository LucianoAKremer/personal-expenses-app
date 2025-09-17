package com.LucianoAKremer.personal_expenses_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LucianoAKremer.personal_expenses_app.data.Expenses.Expense
import com.LucianoAKremer.personal_expenses_app.repository.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map

class ExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {

    // Flujo de lista de gastos
    val allExpenses: StateFlow<List<Expense>> = repository.allExpenses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Flujo del total de gastos
    val totalExpenses: StateFlow<Double> = repository.totalExpenses
        .map { it ?: 0.0 }  // <- aseguramos que nunca sea null
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Insertar gasto
    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repository.insert(expense)
        }
    }

    // Eliminar gasto
    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.delete(expense)
        }
    }
}