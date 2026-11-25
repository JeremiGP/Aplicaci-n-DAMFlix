package com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel

import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local.PeliculaEntity

/**
 * Interfaz sellada que modela el estado de la UI para la pantalla de detalle de una película.
 *
 * Permite manejar de forma reactiva y segura los 3 estados posibles durante la carga de datos.
 */
sealed interface DetalleUiState {
    /** Estado mientras la película está cargándose asíncronamente en segundo plano */
    object Cargando : DetalleUiState

    /** Estado cuando la consulta ha tenido éxito y se dispone de la película */
    data class Exito(val pelicula: PeliculaEntity) : DetalleUiState

    /** Estado cuando ha ocurrido un error o la película no ha sido encontrada */
    data class Error(val mensaje: String) : DetalleUiState
}
