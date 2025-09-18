package com.LucianoAKremer.personal_expenses_app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date // Mantienes java.util.Date aquí

@Entity(
    tableName = "expenses",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],      // Columna 'id' en la tabla 'categories'
        childColumns = ["categoryId"], // Columna 'categoryId' en la tabla 'expenses'
        onDelete = ForeignKey.SET_DEFAULT, // Acción al eliminar una categoría referenciada
        onUpdate = ForeignKey.NO_ACTION  // Acción al actualizar una categoría referenciada (NO_ACTION es común)
    )],
    indices = [Index(value = ["categoryId"])] // Índice para mejorar el rendimiento de las búsquedas por categoryId
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val amount: Double,

    // categoryId es la clave foránea que referencia a la tabla 'categories'
    // Debe ser nullable si quieres permitir gastos sin categoría o si ON DELETE SET DEFAULT puede resultar en NULL.
    val categoryId: Int?,

    val note: String?,

    // Room almacenará esto como INTEGER (Long) usando tu TypeConverter
    val date: Date
)
