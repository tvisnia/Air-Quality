package pl.wisniewskit.airquality.data.local.db

import androidx.room.*
import javax.inject.Inject
import pl.wisniewskit.airquality.entity.AQStation
import pl.wisniewskit.airquality.logic.repository.LocalStationsRepository


class DatabaseStationsRepository @Inject constructor(private val database: AppDatabase) :
    LocalStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        val stationEntities = database.stationsDao().getAll()
        return stationEntities.map { station ->
            AQStation(
                station.uid,
                station.name,
                station.city,
                station.sponsor,
                station.sponsorImage
            )
        }
    }

    override suspend fun save(stations: List<AQStation>) {
        database.stationsDao().insert(stations.map {
            StationEntity(
                it.id,
                it.name,
                it.city,
                it.sponsor,
                it.sponsorImage
            )
        })
    }

}

@Entity
data class StationEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "sponsor") val sponsor: String,
    @ColumnInfo(name = "sponsor_image") val sponsorImage: String?
)

@Dao
interface StationsDao {
    @Query("SELECT * from stationentity")
    suspend fun getAll(): List<StationEntity>

    @Insert
    suspend fun insert(stations: List<StationEntity>)
}

@Database(entities = [StationEntity::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun stationsDao(): StationsDao
}