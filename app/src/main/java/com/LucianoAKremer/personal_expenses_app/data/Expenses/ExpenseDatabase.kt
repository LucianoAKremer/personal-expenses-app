package com.LucianoAKremer.personal_expenses_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.LucianoAKremer.personal_expenses_app.data.Expenses.ExpenseDao

@Database(entities = [Expense::class, Category::class], version = 2, exportSchema = false) // Incrementa la versión
@TypeConverters(Converters::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao // Añade esto

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expenses_db"
                )
                    .addMigrations(MIGRATION_1_2) // Añade la migración
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Define tu migración aquí
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // 1. Crear la nueva tabla de categorías
                db.execSQL("""
                        CREATE TABLE IF NOT EXISTS `categories` (
                            `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                            `name` TEXT NOT NULL
                        )
                    """.trimIndent())

                // 2. Añadir la columna categoryId a la tabla expenses
                db.execSQL("ALTER TABLE `expenses` ADD COLUMN `categoryId` INTEGER")

                // 3. (Opcional) Poblar la tabla de categorías con las categorías existentes en la columna 'category' de 'expenses'
                //    Y actualizar 'categoryId' en 'expenses'. Esto es más complejo y depende de cómo quieras manejarlo.
                //    Una forma simple es crear categorías por defecto o permitir al usuario recategorizar.
                //    Por ahora, dejaremos categoryId como NULLABLE y el usuario podrá asignar categorías después.

                // Crear un índice para la nueva columna si no se crea automáticamente con ForeignKey (depende de Room)
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_categoryId` ON `expenses` (`categoryId`)")
            }
        }
    }
}
    