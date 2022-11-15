package pl.wisniewskit.airquality.usecase

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import pl.wisniewskit.airquality.entity.AQStation
import pl.wisniewskit.airquality.logic.repository.RemoteStationsRepository
import pl.wisniewskit.airquality.logic.usecase.GetStationsUseCase


class GetStationsUseCaseTest {

    @Test
    fun init_DoesNotMakeAnyRemoteOrLocalCalls() {
        val remote = MockRemoteStationsRepository()
        val sut = GetStationsUseCase(remoteStationsRepository = remote)
        assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeMakesOneCallToRemote() = runBlocking {
        val remote = MockRemoteStationsRepository()
        val sut = GetStationsUseCase(remoteStationsRepository = remote)
        sut.execute()
        assertEquals(1, remote.getAllCallsCount)
    }
}

class MockRemoteStationsRepository : RemoteStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        getAllCalled = true
        getAllCallsCount++
        return listOf()
    }

    var getAllCalled: Boolean = false
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0
}