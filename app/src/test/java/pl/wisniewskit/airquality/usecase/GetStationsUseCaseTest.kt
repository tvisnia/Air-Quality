package pl.wisniewskit.airquality.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import pl.wisniewskit.airquality.entity.AQStation
import pl.wisniewskit.airquality.logic.repository.LocalStationsRepository
import pl.wisniewskit.airquality.logic.repository.RemoteStationsRepository
import pl.wisniewskit.airquality.logic.usecase.GetStationUseCase


class GetStationUseCaseTest {

    lateinit var sut: GetStationUseCase
    lateinit var remote: MockRemoteStationsRepository
    lateinit var local: MockLocalStationsRepository
    lateinit var sampleAQStation: AQStation

    @Before
    fun setUp() {
        local = MockLocalStationsRepository()
        remote = MockRemoteStationsRepository()
        sut = GetStationUseCase(remoteStationRepository = remote, localStationsRepository = local)
        sampleAQStation = AQStation("1", "Name", "Lublin", "Sponsor", "")
    }

    @Test
    fun init_DoesNotMakeAnyRemoteOrLocalCalls() {
        assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeMakesOneCallToLocal() = runBlocking {
        sut.execute()
        assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeMakesCallToRemoteWhenLocalDataIsEmpty() = runBlocking {
        //GIVEN
        local.getAllResult = emptyList()
        //WHEN
        sut.execute()

        assertEquals(true, remote.getAllCalled)
    }

    @Test
    fun executeDoesNotMakesCallToRemoteWhenLocalDataIsNotEmpty() = runBlocking {
        //GIVEN
        local.getAllResult = listOf(sampleAQStation)
        //WHEN
        sut.execute()
        //THEN
        assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeDoesMakesOneCallToLocal() = runBlocking {

        sut.execute()

        assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeDoesMakesOneCallToLocalForNoneEmptyData() = runBlocking {
        //GIVEN
        local.getAllResult = listOf(sampleAQStation)
        //WHEN
        sut.execute()
        //THEN
        assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeReturnsReportsStationsWhenRemoteStationRepositoryIsCalled() = runBlocking {
        //GIVEN
        local.getAllResult = emptyList()
        remote.getAllResult = listOf(sampleAQStation)
        //WHEN
        val actual = sut.execute()
        //THEN
        assertEquals("1", actual.first().id)
    }

    @Test
    fun executeReturnsLocalStationsWhenRemoteStationRepositoryIsCalled() = runBlocking {
        //GIVEN
        local.getAllResult = listOf(sampleAQStation)
        //WHEN
        val actual = sut.execute()
        //THEN
        assertEquals("1", actual.first().id)
    }

    @Test
    fun executeReturnsLocalStationsWhenLocalRepositoryIsNotEmpty() = runBlocking {
        //GIVEN
        local.getAllResult = listOf(sampleAQStation)
        //WHEN
        val actual = sut.execute()
        //THEN
        assertEquals("1", actual.first().id)
    }

    @Test
    fun executeSavesStationsToLocalWhenRemoteIsNotEmpty() = runBlocking {
        //GIVEN
        local.getAllResult = emptyList()
        remote.getAllResult = listOf(sampleAQStation)
        //WHEN
        sut.execute()
        //THEN
        assertEquals(true, local.saveCalled)
        assertEquals("1", local.saveReceivedArguments.first().id)
    }

    @Test
    fun executeReturnsValidLocalListStations() = runBlocking {
        //GIVEN
        val sampleAQStation2 = AQStation("2", "Name", "Lublin", "Sponsor", "")

        local.getAllResult = listOf(sampleAQStation, sampleAQStation2)
        //WHEN
        val actual = sut.execute()
        //THEN
        assertEquals("1", actual.first().id)
        assertEquals("2", actual.last().id)
    }
}

class MockLocalStationsRepository : LocalStationsRepository {
    val getAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0

    val saveCalled: Boolean
        get() = saveCallsCount > 0
    var saveCallsCount: Int = 0

    var getAllResult: List<AQStation> = emptyList()

    var saveReceivedArguments: List<AQStation> = emptyList()

    override suspend fun getAll(): List<AQStation> {
        getAllCallsCount += 1
        return getAllResult
    }

    override suspend fun save(stations: List<AQStation>) {
        saveReceivedArguments = stations
        saveCallsCount += 1
    }
}

class MockRemoteStationsRepository : RemoteStationsRepository {
    var getAllResult: List<AQStation> = emptyList()

    val getAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0

    override suspend fun getAll(): List<AQStation> {
        getAllCallsCount += 1
        return getAllResult
    }
}