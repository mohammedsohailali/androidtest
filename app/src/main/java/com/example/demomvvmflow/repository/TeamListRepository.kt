package com.example.demomvvmflow.repository

import com.example.demomvvmflow.data.base.remote.BaseApi
import com.example.demomvvmflow.data.model.user.response.TeamListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class TeamListRepository(private val baseApi: BaseApi) {
    suspend fun getTeams(): Flow<TeamListResponse> {
        return flow {
            val userList = baseApi.getTeams()
            emit(userList)
        }.flowOn(Dispatchers.IO)
    }
}