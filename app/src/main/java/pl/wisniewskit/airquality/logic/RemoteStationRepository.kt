package pl.wisniewskit.airquality.logic

import pl.wisniewskit.airquality.entity.AQStation

interface RemoteStationsRepository {
    suspend fun getAll(): List<AQStation>
}