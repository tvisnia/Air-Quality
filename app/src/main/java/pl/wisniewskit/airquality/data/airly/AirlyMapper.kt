package pl.wisniewskit.airquality.data.airly

import pl.wisniewskit.airquality.entity.AQStation

class AirlyMapper {
    fun mapInstallation(installation: AirlyDTO.Installation): AQStation {
        return AQStation(
            id = installation.id.toString(),
            name = installation.address?.displayAddress2 ?: "Unknown",
            city = installation.address?.city ?: "Unknown",
            sponsor = installation.sponsor?.displayName ?: "Unknown",
            sponsorImage = installation.sponsor?.logo
        )
    }
}