package pl.wisniewskit.airquality.data.airly

import retrofit2.http.GET

interface AirlyService {
    @GET(AirlyEndpoint.INSTALLATIONS)
    suspend fun getInstallations(): List<AirlyDTO.Installation>
}