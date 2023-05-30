package com.example.livescoreapp.liveScore.ui.viewmodel.state

import com.example.livescoreapp.liveScore.data.remote.model.ModelMatch

sealed class MatchesState{
    object Empty: MatchesState()
    object Loading: MatchesState()
    class Success(val data: List<ModelMatch>) : MatchesState()
    class Error(val message:String):MatchesState()
}
