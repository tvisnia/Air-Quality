package pl.wisniewskit.airquality.logic

import pl.wisniewskit.airquality.entity.AQStation
import pl.wisniewskit.airquality.logic.repository.RemoteStationsRepository
import javax.inject.Singleton

@Singleton
class FakeRemoteStationsRepository: RemoteStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        return emptyList()
    }

}