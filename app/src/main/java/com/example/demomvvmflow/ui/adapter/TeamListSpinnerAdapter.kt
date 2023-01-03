package com.example.demomvvmflow.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.demomvvmflow.R
import com.example.demomvvmflow.data.model.user.response.TeamListResponse
import com.example.demomvvmflow.databinding.ItemSpinnerTeamListBinding


class TeamListSpinnerAdapter(
    private val teamList: ArrayList<TeamListResponse.Data>
) : BaseAdapter() {
    override fun getCount(): Int {
        return teamList.size
    }

    override fun getItem(pos: Int): Any {
        return teamList[pos]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val itemSpinnerTeamListBinding: ItemSpinnerTeamListBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(p2?.context),
                R.layout.item_spinner_team_list, p2, false
            )

        itemSpinnerTeamListBinding.team = teamList[p0]
        return itemSpinnerTeamListBinding.root
    }


}