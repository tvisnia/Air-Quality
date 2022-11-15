package pl.wisniewskit.airquality.data

import pl.wisniewskit.airquality.data.airly.AirlyMapper
import pl.wisniewskit.airquality.data.airly.AirlyService
import pl.wisniewskit.airquality.entity.AQStation
import pl.wisniewskit.airquality.logic.repository.RemoteStationsRepository
import javax.inject.Inject

class AirlyStationDataSource @Inject constructor(private val airlyService: AirlyService) :
    RemoteStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        val installations = airlyService.getInstallations()
        return installations.map { AirlyMapper().mapInstallation(it) }
    }
}