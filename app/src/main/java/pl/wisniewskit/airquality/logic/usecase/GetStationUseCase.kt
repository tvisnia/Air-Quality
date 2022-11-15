package pl.wisniewskit.airquality.logic.usecase

import pl.wisniewskit.airquality.entity.AQStation
import pl.wisniewskit.airquality.logic.repository.LocalStationsRepository
import pl.wisniewskit.airquality.logic.repository.RemoteStationsRepository
import javax.inject.Inject

class GetStationUseCase @Inject constructor(
    private val remoteStationRepository: RemoteStationsRepository,
    private val localStationsRepository: LocalStationsRepository
)
{

    suspend fun execute() : List<AQStation>{
        val localStations = localStationsRepository.getAll()
        if(localStations.isEmpty()) {
            val remoteStationRepository = remoteStationRepository.getAll()
            localStationsRepository.save(remoteStationRepository)
            return remoteStationRepository
        }
        return localStations
    }
}