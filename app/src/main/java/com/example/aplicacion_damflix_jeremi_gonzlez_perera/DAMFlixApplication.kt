package com.example.aplicacion_damflix_jeremi_gonzlez_perera

import android.app.Application
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.repository.PeliculaRepository
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local.AppDatabase

class DAMFlixApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { PeliculaRepository(database.peliculaDao()) }
}
