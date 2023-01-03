package com.example.demomvvmflow.ui.viewmodel

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.viewModelScope
import com.example.demomvvmflow.data.base.remote.BaseService
import com.example.demomvvmflow.data.model.user.response.TeamListResponse
import com.example.demomvvmflow.repository.TeamListRepository
import com.example.demomvvmflow.util.isNetworkAvailable
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TeamViewModel : BaseObservableViewModel() {

    private val teamListRepository = BaseService().getBaseApi()?.let { TeamListRepository(it) }


    private val _teamListSharedFlow = MutableSharedFlow<TeamListResponse>()
    val teamListSharedFlow = _teamListSharedFlow as SharedFlow<TeamListResponse>
    private val _loadingSharedFlow = MutableSharedFlow<Boolean>()
    val loadingSharedFlow = _loadingSharedFlow as SharedFlow<Boolean>
    private val _errorSharedFlow = MutableSharedFlow<String>()
    val errorSharedFlow = _errorSharedFlow as SharedFlow<String>

    fun callTeamListApi(root: ConstraintLayout) {
        if (isNetworkAvailable()) {
            viewModelScope.launch {
                _loadingSharedFlow.emit(true)
            }

            viewModelScope.launch {
                teamListRepository?.getTeams()?.catch {
                    _loadingSharedFlow.emit(false)
                    _errorSharedFlow.emit(it.message.toString())

                }?.collect {
                    _loadingSharedFlow.emit(false)
                    _teamListSharedFlow.emit(it)
                }
            }
        } else {
            Snackbar.make(
                root,
                "Please check the Internet connection and try again.",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}