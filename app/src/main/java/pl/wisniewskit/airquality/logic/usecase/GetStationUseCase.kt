package pl.wisniewskit.airquality.logic.usecase

import pl.wisniewskit.airquality.entity.AQStation
import pl.wisniewskit.airquality.logic.repository.RemoteStationsRepository
import javax.inject.Inject

class GetStationsUseCase @Inject constructor(
    private val remoteStationsRepository: RemoteStationsRepository
) {

    suspend fun execute(): List<AQStation> {
        return remoteStationsRepository.getAll()
    }
}