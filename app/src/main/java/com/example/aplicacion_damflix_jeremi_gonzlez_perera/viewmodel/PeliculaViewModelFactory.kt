package com.example.aplicacion_damflix_jeremi_gonzlez_perera.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.PeliculaRepository

class PeliculaViewModelFactory(
    private val repository: PeliculaRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeliculaViewModel::class.java)) {
            return PeliculaViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel no reconocida por la fábrica: ${modelClass.name}")
    }
}
