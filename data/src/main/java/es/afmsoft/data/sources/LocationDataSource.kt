package es.afmsoft.data.sources

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}
