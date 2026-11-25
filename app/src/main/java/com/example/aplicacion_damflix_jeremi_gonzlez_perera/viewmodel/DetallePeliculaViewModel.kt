package com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.repository.PeliculaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel encargado de gestionar el estado de la interfaz de la pantalla de detalle.
 *
 * Realiza la consulta asíncrona a Room mediante corrutinas en un hilo secundario (`Dispatchers.IO`).
 *
 * @property repository Instancia del repositorio para consultar los datos de la película.
 */
class DetallePeliculaViewModel(
    private val repository: PeliculaRepository
) : ViewModel() {

    // Estado interno mutable inicializado en estado de carga
    private val _uiState = MutableStateFlow<DetalleUiState>(DetalleUiState.Cargando)

    // Estado expuesto a la vista como un StateFlow de solo lectura
    val uiState: StateFlow<DetalleUiState> = _uiState.asStateFlow()

    /**
     * Carga de manera asíncrona los detalles de una película a partir de su ID.
     *
     * @param peliculaId Identificador de la película a consultar.
     */
    fun cargarDetallePelicula(peliculaId: Int) {
        viewModelScope.launch {
            _uiState.value = DetalleUiState.Cargando
            // Ejecuta la consulta a la base de datos en el hilo I/O de entrada/salida
            val resultado = withContext(Dispatchers.IO) {
                kotlinx.coroutines.delay(1000) // Retardo simulado para poder visualizar el indicador de carga
                repository.obtenerPorId(peliculaId)
            }
            if (resultado != null) {
                _uiState.value = DetalleUiState.Exito(resultado)
            } else {
                _uiState.value = DetalleUiState.Error("Película no encontrada.")
            }
        }
    }
}
