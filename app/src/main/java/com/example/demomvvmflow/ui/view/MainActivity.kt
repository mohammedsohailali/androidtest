package com.example.demomvvmflow.ui.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.demomvvmflow.data.model.user.response.TeamListResponse
import com.example.demomvvmflow.databinding.ActivityMainBinding
import com.example.demomvvmflow.ui.adapter.TeamListSpinnerAdapter
import com.example.demomvvmflow.ui.viewmodel.TeamViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    private lateinit var teamViewModel: TeamViewModel
    private lateinit var teamListSpinnerAdapter: TeamListSpinnerAdapter
    private lateinit var binding: ActivityMainBinding
    private var teamArrayList: ArrayList<TeamListResponse.Data> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()

        binding.spinnerSelectTeams.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val teamData = teamArrayList[p2]
                    binding.txtName.text = teamData.name
                    binding.txtCity.text = teamData.city
                    binding.txtConference.text = teamData.conference
                    binding.txtDivision.text = teamData.division
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            teamViewModel.teamListSharedFlow.collectLatest { teamList ->
                setTeamSpinnerList(teamList.data)
            }
        }

        lifecycleScope.launchWhenCreated {
            teamViewModel.errorSharedFlow.collectLatest {
                Toast.makeText(this@MainActivity, "" + it, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        lifecycleScope.launchWhenCreated {
            teamViewModel.loadingSharedFlow.collectLatest {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setTeamSpinnerList(teamList: List<TeamListResponse.Data>) {
        teamArrayList.addAll(teamList)
        teamListSpinnerAdapter = TeamListSpinnerAdapter(teamArrayList)
        binding.spinnerSelectTeams.adapter = teamListSpinnerAdapter
        binding.spinnerSelectTeams.setSelection(0)
    }

    private fun initViews() {
        teamViewModel = ViewModelProvider(this)[TeamViewModel::class.java]
        teamViewModel.callTeamListApi(binding.root)

    }

}