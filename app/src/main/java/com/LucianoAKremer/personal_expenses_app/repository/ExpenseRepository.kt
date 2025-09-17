package com.LucianoAKremer.personal_expenses_app.repository

import com.LucianoAKremer.personal_expenses_app.data.Expenses.Expense
import com.LucianoAKremer.personal_expenses_app.data.Expenses.ExpenseDao
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpenses: Flow<List<Expense>> = expenseDao.getAllExpenses()
    val totalExpenses: Flow<Double?> = expenseDao.getTotalExpenses()

    suspend fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }
}