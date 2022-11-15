package pl.wisniewskit.airquality.logic.repository

import pl.wisniewskit.airquality.entity.AQStation

interface RemoteStationsRepository {
    suspend fun getAll() : List<AQStation>
}