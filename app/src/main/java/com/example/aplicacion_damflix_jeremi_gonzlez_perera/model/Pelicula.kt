package com.example.aplicacion_damflix_jeremi_gonzlez_perera.model

data class Pelicula(
    val id: Int,
    val titulo: String,
    val director: String,
    val fechaEstreno: String,
    val genero: String,
    val pendiente: Boolean,
    val esFavorita: Boolean = false
)
