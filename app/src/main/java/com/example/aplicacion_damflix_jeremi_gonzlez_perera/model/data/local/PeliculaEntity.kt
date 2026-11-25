package com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa la tabla "peliculas" en la base de datos local de Room.
 *
 * Cada instancia de esta clase corresponde a una fila en la tabla de películas.
 *
 * @property id Identificador único de la película (Clave Primaria autogenerada por Room).
 * @property titulo Título principal de la película.
 * @property director Nombre del director o directora de la película.
 * @property anio Año de estreno numérico.
 * @property fechaEstreno Fecha de estreno en formato de texto.
 * @property genero Género cinematográfico de la película.
 * @property pendiente Indica si la película está pendiente de ver.
 * @property esVista Indica si la película ya ha sido vista.
 * @property esFavorita Indica si el usuario la ha marcado como favorita.
 */
@Entity(tableName = "peliculas")
data class PeliculaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val director: String,
    val anio: Int = 0,
    val fechaEstreno: String = "",
    val genero: String = "",
    val pendiente: Boolean = false,
    val esVista: Boolean = false,
    val esFavorita: Boolean = false
)
