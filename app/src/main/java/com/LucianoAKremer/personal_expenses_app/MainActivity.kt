package com.LucianoAKremer.personal_expenses_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.LucianoAKremer.personal_expenses_app.data.ExpenseDatabase
import com.LucianoAKremer.personal_expenses_app.data.Expenses.ExpenseDatabase
import com.LucianoAKremer.personal_expenses_app.repository.ExpenseRepository
import com.LucianoAKremer.personal_expenses_app.ui.ExpensesScreen
import com.LucianoAKremer.personal_expenses_app.viewmodel.ExpenseViewModel
import com.LucianoAKremer.personal_expenses_app.viewmodel.ExpenseViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = ExpenseDatabase.getDatabase(applicationContext) // Nueva forma

        // Crear repo
        val repository = ExpenseRepository(db.expenseDao())

        // Crear ViewModel
        viewModel = ViewModelProvider(
            this,
            ExpenseViewModelFactory(repository)
        )[ExpenseViewModel::class.java]

        // Lanzar UI con Compose
        setContent {
            ExpensesScreen(viewModel)
        }
    }
}