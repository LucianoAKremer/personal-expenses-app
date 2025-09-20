package com.LucianoAKremer.personal_expenses_app.repository


import com.LucianoAKremer.personal_expenses_app.data.Category
import com.LucianoAKremer.personal_expenses_app.data.CategoryDao
import com.LucianoAKremer.personal_expenses_app.data.Expense
import com.LucianoAKremer.personal_expenses_app.data.ExpenseDao
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(
    private val expenseDao: ExpenseDao,
    private val categoryDao: CategoryDao // Añade CategoryDao
) {

    val allExpenses: Flow<List<Expense>> = expenseDao.getAllExpenses()
    val totalExpenses: Flow<Double?> = expenseDao.getTotalExpenses()
    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories() // Nuevo flujo para categorías

    suspend fun insert(expense: Expense) {
        expenseDao.insert(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.delete(expense)
    }

    suspend fun insertCategory(category: Category) { // Nuevo método
        categoryDao.insert(category)
    }

    suspend fun getCategoryByName(name: String): Category? { // Nuevo método
        return categoryDao.getCategoryByName(name)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.delete(category)
    }
}