package com.example.livescoreapp.liveScore.data.repository

import com.example.livescoreapp.liveScore.data.remote.ApiService
import com.example.livescoreapp.liveScore.data.remote.model.ModelMatch
import javax.inject.Inject

class CurrentMatchesRepository @Inject constructor(private val elenaApiService: ApiService) {

    suspend fun getAllCurrentMatches(): List<ModelMatch> = elenaApiService.getInPlayMatches().data
}