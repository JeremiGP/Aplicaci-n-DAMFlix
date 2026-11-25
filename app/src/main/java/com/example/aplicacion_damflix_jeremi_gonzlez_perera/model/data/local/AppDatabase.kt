package com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Base de datos principal de la aplicación utilizando Room ORM.
 *
 * Registra la entidad [PeliculaEntity] y expone el DAO [PeliculaDao].
 * Implementa el patrón Singleton para garantizar una única instancia de la base de datos abierta en la app.
 */
@Database(entities = [PeliculaEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /** Retorna el DAO para interactuar con las películas */
    abstract fun peliculaDao(): PeliculaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Obtiene o crea la instancia única de la base de datos Room.
         *
         * Usa [synchronized] para evitar condiciones de carrera en entornos multihilo.
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "damflix_room.db"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
