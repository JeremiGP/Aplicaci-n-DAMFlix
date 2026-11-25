package com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.repository

import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local.PeliculaDao
import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local.PeliculaEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio de datos de películas.
 *
 * Actúa como intermediario entre la capa de datos local (Room DAO) y los ViewModels,
 * abstrayendo el origen de los datos de la lógica de presentación.
 *
 * @property dao Instancia de [PeliculaDao] para ejecutar operaciones en la base de datos.
 */
class PeliculaRepository(private val dao: PeliculaDao) {

    /** Flow que emite la lista actualizada de películas automáticamente ante cambios */
    val todasPeliculas: Flow<List<PeliculaEntity>> = dao.obtenerTodas()

    /**
     * Consulta una película específica por su ID.
     */
    suspend fun obtenerPorId(id: Int): PeliculaEntity? {
        return dao.obtenerPorId(id)
    }

    /**
     * Inserta una nueva película en la base de datos.
     */
    suspend fun insertar(pelicula: PeliculaEntity) {
        dao.insertar(pelicula)
    }

    /**
     * Actualiza los datos de una película.
     */
    suspend fun actualizar(pelicula: PeliculaEntity) {
        dao.actualizar(pelicula)
    }

    /**
     * Elimina una película de la base de datos.
     */
    suspend fun eliminar(pelicula: PeliculaEntity) {
        dao.eliminar(pelicula)
    }
}
