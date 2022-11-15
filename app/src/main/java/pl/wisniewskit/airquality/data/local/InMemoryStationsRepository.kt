package pl.wisniewskit.airquality.data.local

import pl.wisniewskit.airquality.entity.AQStation
import pl.wisniewskit.airquality.logic.repository.LocalStationsRepository

class InMemoryStationsRepository : LocalStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        TODO("Not yet implemented")
    }

    override suspend fun save(stations: List<AQStation>) {
        TODO("Not yet implemented")
    }
}