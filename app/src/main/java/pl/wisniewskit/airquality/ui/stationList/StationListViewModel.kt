package pl.wisniewskit.airquality.ui.stationList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class StationListViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(
        State(stations = listOf())
    )

    init {
        loadStations()
    }

    private fun loadStations() {
        state = State(stations = listOf("Stacja 1", "Stacja 2", "Stacja 3"))
    }

    data class State(
        val stations: List<String> = listOf()
    )
}

