package com.example.aplicacion_damflix_jeremi_gonzlez_perera.model

import com.google.firebase.firestore.DocumentId

data class PeliculaCloud(
    @DocumentId val id: String = "",
    val titulo: String = "",
    val director: String = "",
    val anio: Int = 0,
    val esVista: Boolean = false,
    val esFavorita: Boolean = false,
    val posterUrl: String = ""
)
