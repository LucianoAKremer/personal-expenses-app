package com.LucianoAKremer.personal_expenses_app.data // O el paquete correcto de tu DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.LucianoAKremer.personal_expenses_app.data.Expense // Asegúrate que esta importación sea correcta
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    // Asegúrate de que 'Expense' aquí se resuelva a tu clase Entity
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT SUM(amount) FROM expenses")
    fun getTotalExpenses(): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: Expense) // Aquí también

    @Delete
    suspend fun delete(expense: Expense) // Y aquí
}