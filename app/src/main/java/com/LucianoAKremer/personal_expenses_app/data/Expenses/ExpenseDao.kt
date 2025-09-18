package com.LucianoAKremer.personal_expenses_app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    // Query para obtener todos los gastos, ordenados por fecha descendente
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    // Query para obtener la suma total de los montos de todos los gastos
    @Query("SELECT SUM(amount) FROM expenses")
    fun getTotalExpenses(): Flow<Double?> // Puede ser null si no hay gastos

    // Insertar un nuevo gasto. Si hay conflicto (mismo ID), reemplaza.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense)

    // Eliminar un gasto existente
    @Delete
    suspend fun delete(expense: Expense)

    // Si necesitas queries que filtren por categoryId, puedes añadirlas aquí
    // Ejemplo:
    // @Query("SELECT * FROM expenses WHERE categoryId = :categoryId ORDER BY date DESC")
    // fun getExpensesByCategory(categoryId: Int): Flow<List<Expense>>
}