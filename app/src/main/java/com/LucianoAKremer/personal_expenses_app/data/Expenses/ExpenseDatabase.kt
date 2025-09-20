package com.LucianoAKremer.personal_expenses_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Expense::class, Category::class], // Ambas entidades
    version = 2, // ¡IMPORTANTE! Incrementa la versión a 2
    exportSchema = true // Recomendado: exportar esquema para futuras referencias y auto-migraciones
)
@TypeConverters(Converters::class)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao

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
                    .addMigrations(MIGRATION_1_2) // <--- AÑADE TU MIGRACIÓN AQUÍ
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // --- TU MIGRACIÓN DE VERSIÓN 1 A VERSIÓN 2 ---
        // Dentro de tu MIGRATION_1_2 en ExpenseDatabase.kt

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // --- PASO 1: Crear/Asegurar la tabla 'categories' ---
                db.execSQL("""
            CREATE TABLE IF NOT EXISTS `categories` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL 
            )
        """.trimIndent()) // Removí UNIQUE de aquí para añadir el índice explícitamente abajo

                // AÑADIR EL ÍNDICE ÚNICO EXPLÍCITAMENTE CON EL NOMBRE ESPERADO POR ROOM
                db.execSQL("""
            CREATE UNIQUE INDEX IF NOT EXISTS `index_categories_name` 
            ON `categories` (`name`)
        """.trimIndent())


                // ... (el resto de tu migración para la tabla 'expenses' como la tenías antes) ...

                // PASO 2: Crear la nueva tabla 'expenses_new' con el esquema CORRECTO
                db.execSQL("""
            CREATE TABLE expenses_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                amount REAL NOT NULL,
                note TEXT,
                date INTEGER NOT NULL, 
                categoryId INTEGER,  
                FOREIGN KEY(categoryId) REFERENCES categories(id) ON DELETE SET DEFAULT ON UPDATE NO ACTION
            )
        """.trimIndent())

                // PASO 3: Copiar datos de la tabla 'expenses' antigua a 'expenses_new'
                db.execSQL("""
            INSERT INTO expenses_new (id, amount, note, date, categoryId)
            SELECT 
                e.id, 
                e.amount, 
                e.note, 
                e.date,
                (SELECT c.id FROM categories c WHERE c.name = e.category COLLATE NOCASE) 
            FROM expenses e
        """.trimIndent())

                // PASO 4: Eliminar la antigua tabla 'expenses'
                db.execSQL("DROP TABLE expenses")

                // PASO 5: Renombrar 'expenses_new' a 'expenses'
                db.execSQL("ALTER TABLE expenses_new RENAME TO expenses")

                // PASO 6: Crear el índice en la nueva tabla 'expenses' (Room lo espera)
                db.execSQL("CREATE INDEX IF NOT EXISTS index_expenses_categoryId ON expenses(categoryId)")
            }
        }
    }
}
