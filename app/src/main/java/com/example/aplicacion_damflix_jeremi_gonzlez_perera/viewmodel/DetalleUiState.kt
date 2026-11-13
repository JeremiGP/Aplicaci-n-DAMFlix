package com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel

import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local.PeliculaEntity

sealed interface DetalleUiState {
    object Cargando : DetalleUiState
    data class Exito(val pelicula: PeliculaEntity) : DetalleUiState
    data class Error(val mensaje: String) : DetalleUiState
}
