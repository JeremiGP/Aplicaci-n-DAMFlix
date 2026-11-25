package com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para la entidad [PeliculaEntity].
 *
 * Define las consultas y operaciones SQL sobre la tabla de películas en la base de datos Room.
 */
@Dao
interface PeliculaDao {

    /**
     * Obtiene la lista completa de películas ordenadas descendentemente por su ID.
     * Retorna un [Flow] para que la interfaz se actualice automáticamente ante cualquier cambio en la base de datos.
     */
    @Query("SELECT * FROM peliculas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<PeliculaEntity>>

    /**
     * Consulta una película específica por su identificador único.
     * Es una función suspendida para ejecutarse dentro de corrutinas en segundo plano.
     *
     * @param id Identificador de la película.
     * @return [PeliculaEntity] si se encuentra, o null si no existe.
     */
    @Query("SELECT * FROM peliculas WHERE id = :id")
    suspend fun obtenerPorId(id: Int): PeliculaEntity?

    /**
     * Inserta una nueva película en la base de datos.
     * En caso de conflicto de clave primaria, reemplaza la entrada existente.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(pelicula: PeliculaEntity)

    /**
     * Actualiza los datos de una película existente en la base de datos.
     */
    @Update
    suspend fun actualizar(pelicula: PeliculaEntity)

    /**
     * Elimina una película de la base de datos.
     */
    @Delete
    suspend fun eliminar(pelicula: PeliculaEntity)
}
