package com.example.livescoreapp.liveScore.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livescoreapp.liveScore.data.repository.CurrentMatchesRepository
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
class CurrentMatchesViewModel @Inject constructor(private val currentMatchesRepository: CurrentMatchesRepository):ViewModel(){

    private val _currentMatchesState = MutableStateFlow<MatchesState>(MatchesState.Empty)
    val currentMatchesState:StateFlow<MatchesState> = _currentMatchesState

    init {
        getAllCurrentMatches()
    }


    private fun getAllCurrentMatches(){

        _currentMatchesState.value = MatchesState.Loading

        viewModelScope.launch (Dispatchers.IO){

            try{
                val currenMatchesResponse = currentMatchesRepository.getAllCurrentMatches()
                _currentMatchesState.value = MatchesState.Success(currenMatchesResponse)
            }
            catch (exception: HttpException){

                _currentMatchesState.value = MatchesState.Error("No internet connection")
            }
            catch (exception: IOException){
                _currentMatchesState.value = MatchesState.Error("The Api is not working")
            }

        }

    }

}