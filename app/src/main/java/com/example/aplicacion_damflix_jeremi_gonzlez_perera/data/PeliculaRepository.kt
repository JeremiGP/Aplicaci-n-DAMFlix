package com.example.aplicacion_damflix_jeremi_gonzlez_perera.data

import com.example.aplicacion_damflix_jeremi_gonzlez_perera.data.local.PeliculaDao
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.data.local.PeliculaEntity
import kotlinx.coroutines.flow.Flow

class PeliculaRepository(private val dao: PeliculaDao) {
    val todasPeliculas: Flow<List<PeliculaEntity>> = dao.obtenerTodas()

    suspend fun insertar(pelicula: PeliculaEntity) {
        dao.insertar(pelicula)
    }

    suspend fun actualizar(pelicula: PeliculaEntity) {
        dao.actualizar(pelicula)
    }

    suspend fun eliminar(pelicula: PeliculaEntity) {
        dao.eliminar(pelicula)
    }
}
