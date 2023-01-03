package com.example.demomvvmflow.data.base.remote

import com.example.demomvvmflow.data.model.user.response.TeamListResponse
import com.example.demomvvmflow.util.API_TEAMS_LIST
import retrofit2.http.GET

interface BaseApi {
    @GET(API_TEAMS_LIST)
    suspend fun getTeams(): TeamListResponse
}