package com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.repository.PeliculaRepository

class DetallePeliculaViewModelFactory(
    private val repository: PeliculaRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetallePeliculaViewModel::class.java)) {
            return DetallePeliculaViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel no conocida: \${modelClass.name}")
    }
}
