package com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.repository

import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.PeliculaCloud
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Repositorio CRUD completo para sincronización en tiempo real con Cloud Firestore.
 *
 * Emplea [callbackFlow] para observar los cambios continuos en la nube y corrutinas [.await()]
 * para las operaciones de lectura puntual, inserción, actualización y borrado.
 */
class FirestorePeliculaRepository(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val coleccionPeliculas = db.collection("peliculas")

    /**
     * 1. READ (Listado reactivo en tiempo real)
     * Suscribe un listener a la colección en la nube mediante [callbackFlow].
     */
    fun observarPeliculas(): Flow<List<PeliculaCloud>> = callbackFlow {
        val listener = coleccionPeliculas.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val lista = snapshot?.toObjects(PeliculaCloud::class.java).orEmpty()
            trySend(lista)
        }
        // Libera el listener automáticamente al cancelar la corrutina
        awaitClose { listener.remove() }
    }

    /**
     * 2. READ (Consulta puntual de un documento por su ID)
     */
    suspend fun obtenerPorId(id: String): PeliculaCloud? {
        if (id.isEmpty()) return null
        val snapshot = coleccionPeliculas.document(id).get().await()
        return snapshot.toObject(PeliculaCloud::class.java)
    }

    /**
     * 3. CREATE (Inserción de un nuevo documento en la nube)
     */
    suspend fun guardarPelicula(pelicula: PeliculaCloud) {
        coleccionPeliculas.add(pelicula).await()
    }

    /**
     * 4a. UPDATE PARCIAL (Modifica solo el campo 'esFavorita')
     */
    suspend fun conmutarFavorito(pelicula: PeliculaCloud) {
        if (pelicula.id.isNotEmpty()) {
            coleccionPeliculas.document(pelicula.id)
                .update("esFavorita", !pelicula.esFavorita)
                .await()
        }
    }

    /**
     * 4b. UPDATE COMPLETO (Sobrescribe los datos editados de una película existente)
     */
    suspend fun actualizarPelicula(pelicula: PeliculaCloud) {
        if (pelicula.id.isNotEmpty()) {
            coleccionPeliculas.document(pelicula.id)
                .set(pelicula)
                .await()
        }
    }

    /**
     * 5. DELETE (Eliminación del documento en Firestore)
     */
    suspend fun eliminarPelicula(peliculaId: String) {
        if (peliculaId.isNotEmpty()) {
            coleccionPeliculas.document(peliculaId).delete().await()
        }
    }
}
