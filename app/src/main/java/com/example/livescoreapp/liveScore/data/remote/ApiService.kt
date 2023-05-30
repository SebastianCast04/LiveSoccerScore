package com.example.livescoreapp.liveScore.data.remote

import com.example.livescoreapp.liveScore.core.GET_IN_PLAY_FIXTURES
import com.example.livescoreapp.liveScore.core.GET_UPCOMING_MATCHES
import com.example.livescoreapp.liveScore.data.remote.model.MatchesRightNow
import com.example.livescoreapp.liveScore.data.remote.model.UpcomingMatches
import retrofit2.http.GET

interface ApiService {

    @GET(GET_IN_PLAY_FIXTURES)
    suspend fun getInPlayMatches(): MatchesRightNow

    @GET(GET_UPCOMING_MATCHES)
    suspend fun getUpcomingMatches(): UpcomingMatches
}

