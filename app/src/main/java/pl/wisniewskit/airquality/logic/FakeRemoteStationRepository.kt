package pl.wisniewskit.airquality.logic

import pl.wisniewskit.airquality.entity.AQStation
import javax.inject.Singleton

@Singleton
class FakeRemoteStationsRepository: RemoteStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        return emptyList()
    }

}