package com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.data.remote

import com.example.aplicacion_damflix_jeremi_gonzlez_perera.model.PeliculaCloud
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 1. PeliculaDto (Ajustado al JSON real de TVMaze API)
 */
data class PeliculaDto(
    @SerializedName("id") val remoteId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("premiered") val premiered: String?, // Formato "YYYY-MM-DD"
    @SerializedName("network") val network: NetworkDto?,
    @SerializedName("image") val image: ImageDto?
)

data class NetworkDto(
    @SerializedName("name") val name: String?
)

data class ImageDto(
    @SerializedName("medium") val medium: String?,
    @SerializedName("original") val original: String?
)

/**
 * Wrapper para el endpoint de búsqueda (/search/shows?q=...)
 */
data class SearchResultDto(
    @SerializedName("score") val score: Double,
    @SerializedName("show") val show: PeliculaDto
)

/**
 * Función Mapper: transforma el JSON anidado de TVMaze en nuestro modelo limpio de dominio [PeliculaCloud].
 */
fun PeliculaDto.toDomain(): PeliculaCloud {
    val anioEstreno = this.premiered?.take(4)?.toIntOrNull() ?: 2024
    val cadenaOProductora = this.network?.name ?: "Producción Streaming"
    return PeliculaCloud(
        titulo = this.name.trim(),
        director = cadenaOProductora,
        anio = anioEstreno,
        esVista = false,
        esFavorita = false,
        posterUrl = this.image?.medium.orEmpty()
    )
}

/**
 * 2. PeliculaApiService (Endpoints reales y gratuitos sin API Key de TVMaze)
 */
interface PeliculaApiService {
    // Catálogo inicial: https://api.tvmaze.com/shows
    @GET("shows")
    suspend fun obtenerCatalogoRemoto(): List<PeliculaDto>

    // Búsqueda por título: https://api.tvmaze.com/search/shows?q=batman
    @GET("search/shows")
    suspend fun buscarProducciones(@Query("q") consulta: String): List<SearchResultDto>
}

/**
 * 3. ApiClient (Instancia Singleton perezosa)
 */
object ApiClient {
    private const val BASE_URL = "https://api.tvmaze.com/"

    val apiService: PeliculaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PeliculaApiService::class.java)
    }
}
