package com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "peliculas")
data class PeliculaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val director: String,
    val anio: Int = 0,
    val fechaEstreno: String = "",
    val genero: String = "",
    val pendiente: Boolean = false,
    val esVista: Boolean = false,
    val esFavorita: Boolean = false
)
