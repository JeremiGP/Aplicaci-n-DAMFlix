package com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.R
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.repository.FirestorePeliculaRepository
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.remote.ApiClient
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.remote.toDomain
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.PeliculaCloud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ExplorarUiState {
    object Cargando : ExplorarUiState
    data class Exito(val peliculas: List<PeliculaCloud>) : ExplorarUiState
    data class Error(val mensajeResId: Int) : ExplorarUiState
}

class ExplorarViewModel(
    private val firestoreRepository: FirestorePeliculaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExplorarUiState>(ExplorarUiState.Cargando)
    val uiState: StateFlow<ExplorarUiState> = _uiState.asStateFlow()

    init {
        cargarPeliculasOnline()
    }

    fun cargarPeliculasOnline() {
        viewModelScope.launch {
            _uiState.value = ExplorarUiState.Cargando
            try {
                val dtos = ApiClient.apiService.obtenerCatalogoRemoto()
                val peliculasLimpias = dtos.map { it.toDomain() }
                _uiState.value = ExplorarUiState.Exito(peliculasLimpias)
            } catch (e: IOException) {
                _uiState.value = ExplorarUiState.Error(R.string.error_sin_conexion)
            } catch (e: HttpException) {
                _uiState.value = ExplorarUiState.Error(R.string.error_servidor_http)
            }
        }
    }

    fun buscarOnline(query: String) {
        if (query.isBlank()) {
            cargarPeliculasOnline()
            return
        }
        viewModelScope.launch {
            _uiState.value = ExplorarUiState.Cargando
            try {
                val resultados = ApiClient.apiService.buscarProducciones(query)
                val listaLimpia = resultados.map { it.show.toDomain() }
                _uiState.value = ExplorarUiState.Exito(listaLimpia)
            } catch (e: IOException) {
                _uiState.value = ExplorarUiState.Error(R.string.error_sin_conexion)
            } catch (e: HttpException) {
                _uiState.value = ExplorarUiState.Error(R.string.error_servidor_http)
            }
        }
    }

    fun importarAFirestore(pelicula: PeliculaCloud) {
        viewModelScope.launch {
            firestoreRepository.guardarPelicula(pelicula)
        }
    }
}


class ExplorarViewModelFactory(
    private val firestoreRepository: FirestorePeliculaRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExplorarViewModel::class.java)) {
            return ExplorarViewModel(firestoreRepository) as T
        }
        throw IllegalArgumentException("Clase ViewModel no reconocida: ${modelClass.name}")
    }
}
