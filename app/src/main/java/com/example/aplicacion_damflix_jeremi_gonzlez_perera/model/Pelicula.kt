package com.example.aplicacion_damflix_jeremi_gonzlez_perera.model

data class Pelicula(
    val id: Int = 0,
    val titulo: String,
    val director: String,
    val fechaEstreno: String,
    val genero: String,
    val pendiente: Boolean = false,
    val esVista: Boolean = false,
    val esFavorita: Boolean = false
)
