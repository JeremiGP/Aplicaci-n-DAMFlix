package com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.repository.FirestorePeliculaRepository
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.PeliculaCloud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class FirestorePeliculaViewModel(
    private val repository: FirestorePeliculaRepository
) : ViewModel() {

    val listaPeliculas: StateFlow<List<PeliculaCloud>> = repository.observarPeliculas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _peliculaSeleccionada = MutableStateFlow<PeliculaCloud?>(null)
    val peliculaSeleccionada: StateFlow<PeliculaCloud?> = _peliculaSeleccionada.asStateFlow()

    fun cargarPeliculaPorId(id: String) {
        viewModelScope.launch {
            _peliculaSeleccionada.value = if (id.isBlank()) null else repository.obtenerPorId(id)
        }
    }

    fun limpiarSeleccion() {
        _peliculaSeleccionada.value = null
    }

    fun crearPelicula(titulo: String, director: String, anio: Int, esVista: Boolean) {
        viewModelScope.launch {
            val nueva = PeliculaCloud(
                titulo = titulo,
                director = director,
                anio = anio,
                esVista = esVista
            )
            repository.guardarPelicula(nueva)
        }
    }

    fun editarPelicula(peliculaEditada: PeliculaCloud) {
        viewModelScope.launch {
            repository.actualizarPelicula(peliculaEditada)
        }
    }

    fun conmutarFavorito(pelicula: PeliculaCloud) {
        viewModelScope.launch {
            repository.conmutarFavorito(pelicula)
        }
    }

    fun borrarPelicula(peliculaId: String) {
        viewModelScope.launch {
            repository.eliminarPelicula(peliculaId)
        }
    }
}

class FirestorePeliculaViewModelFactory(
    private val repository: FirestorePeliculaRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirestorePeliculaViewModel::class.java)) {
            return FirestorePeliculaViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel no reconocida: ${modelClass.name}")
    }
}
