package com.LucianoAKremer.personal_expenses_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LucianoAKremer.personal_expenses_app.data.Category // Importa Category
import com.LucianoAKremer.personal_expenses_app.data.Expense
import com.LucianoAKremer.personal_expenses_app.repository.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {

    val allExpenses: StateFlow<List<Expense>> = repository.allExpenses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalExpenses: StateFlow<Double> = repository.totalExpenses
        .map { it ?: 0.0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // Flujo para categorías
    val categories: StateFlow<List<Category>> = repository.allCategories
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            repository.insert(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.delete(expense)
        }
    }

    fun addCategory(categoryName: String) {
        viewModelScope.launch {
            if (categoryName.isNotBlank()) {
                // Opcional: Verificar si la categoría ya existe para evitar duplicados por nombre
                val existingCategory = repository.getCategoryByName(categoryName)
                if (existingCategory == null) {
                    repository.insertCategory(Category(name = categoryName))
                } else {
                    // Manejar el caso de categoría duplicada (ej: mostrar un mensaje)
                }
            }
        }
    }
}
    