package com.example.sub2.past_event


import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar

import com.example.sub2.R
import com.example.sub2.api.ApiRepository
import com.example.sub2.detail_event.DetailEventActivity
import com.example.sub2.model.Event
import com.example.sub2.util.SharedPreference
import com.example.sub2.util.invisible
import com.example.sub2.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_past.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class PastFragment : Fragment(), PastEventView {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: PastEventPresenter
    private lateinit var adapter: PastEventAdapter
    private lateinit var leagueId: String
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val request = ApiRepository()
        val gson = Gson()
        presenter = PastEventPresenter(this, request, gson)
        val view = inflater.inflate(R.layout.fragment_past, container, false)
        progressBar = view.findViewById(R.id.progressBarPast)
        swipeRefresh = view.findViewById(R.id.swipeRefreshPast)
        val rv_event = view.findViewById(R.id.rv_event_past) as RecyclerView
        rv_event.layoutManager = LinearLayoutManager(this.context)
        adapter = context?.let {
            PastEventAdapter(it, events){
                toast(it.strEvent.toString())
                startActivity<DetailEventActivity>("eventBundle" to it)
            }
        }!!
        rv_event.adapter = adapter
        val sharedPreference: SharedPreference = SharedPreference(activity!!.baseContext)
        leagueId = sharedPreference.getValueString("leagueId").toString()
        presenter.getPastEventList(leagueId)
        Log.d("eventsa2", leagueId)
        swipeRefresh.setOnRefreshListener() {
            swipeRefresh.isRefreshing = false
            presenter.getPastEventList(leagueId)
        }
        return view
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing=true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing=false
    }

    override fun showEventList(data: List<Event>) {
        swipeRefresh.isRefreshing=false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showNullData() {
        alert("Past Event not found", "Not Found!") {}.show()
    }


}
