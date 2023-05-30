package com.example.livescoreapp.liveScore.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livescoreapp.liveScore.data.repository.CurrentMatchesRepository
import com.example.livescoreapp.liveScore.data.repository.UpcomingMatchesRepository
import com.example.livescoreapp.liveScore.ui.viewmodel.state.MatchesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class UpcomingMatchesViewModel @Inject constructor(private val upcomingMatchesRepository: UpcomingMatchesRepository) :
    ViewModel() {

    private val _upComingMatchesState = MutableStateFlow<MatchesState>(MatchesState.Empty)
    val upComingMatchesState: StateFlow<MatchesState> = _upComingMatchesState

    init {
        getUpcomingMatches()
    }


    private fun getUpcomingMatches() {

        _upComingMatchesState.value = MatchesState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val upComingMatchesResponse = upcomingMatchesRepository.getAllUpcomingMatches()
                _upComingMatchesState.value = MatchesState.Success(upComingMatchesResponse)
            } catch (exception: HttpException) {

                _upComingMatchesState.value = MatchesState.Error("No internet connection")
            } catch (exception: IOException) {
                _upComingMatchesState.value = MatchesState.Error("The Api is not working")
            }

        }

    }

}