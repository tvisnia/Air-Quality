package pl.wisniewskit.airquality.logic.repository

import pl.wisniewskit.airquality.entity.AQStation

interface LocalStationsRepository {
    suspend fun getAll(): List<AQStation>
    suspend fun save(stations: List<AQStation>)
}