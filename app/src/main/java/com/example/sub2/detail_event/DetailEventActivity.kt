package com.example.sub2.detail_event

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.sub2.R
import com.example.sub2.api.ApiRepository
import com.example.sub2.model.Event
import com.example.sub2.model.Team
import com.example.sub2.util.invisible
import com.example.sub2.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_deatil_event.*
import java.text.SimpleDateFormat
import java.util.*

class DetailEventActivity : AppCompatActivity(), DetailEventView {
    private lateinit var presenter: DetailEventPresenter
    private lateinit var eventId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deatil_event)

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailEventPresenter(this, request, gson)

        val event: Event = intent.getParcelableExtra("eventBundle")
        eventId = event.idEvent.toString()

        presenter.getDetailEvent(eventId)

        swipeRefreshEvent.setOnRefreshListener() {
            swipeRefreshEvent.isRefreshing = false
            presenter.getDetailEvent(eventId)
        }
    }

    override fun showLoading() {
        swipeRefreshEvent.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshEvent.isRefreshing = false
    }

    override fun showDetailEvent(data: List<Event>) {
        val data1 = data[0]
        Log.d("shy", data1.toString())

        val formatDate = SimpleDateFormat("yyy-MM-dd", Locale.getDefault())
        val date = formatDate.parse(data1.dateEvent)
        val dateText = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            .format(date).toString()
        tvDetailEventDate.text = dateText
        tvDetailEventTime.text = data1.strTimeLocal

        tvDetailEventHome?.text = data1.strHomeTeam
        tvDetailEventAway?.text = data1.strAwayTeam
        tvDetailEventAwayFormation?.text = data1.strAwayFormation
        tvDetailEventHomeFormation?.text = data1.strHomeFormation
        tvDetailAwayGoal?.text = data1.intAwayScore
        tvDetailHomeGoal?.text = data1.intHomeScore
        tvDetailAwayShots?.text = data1.intAwayShots
        tvDetailHomeShots?.text = data1.intHomeShots
        tvDetailAwayDefense?.text = data1.strAwayLineupDefense
        tvDetailHomeDefense?.text = data1.strHomeLineupDefense
        tvDetailAwayMidfield?.text = data1.strAwayLineupMidfield
        tvDetailHomeMidfield?.text = data1.strHomeLineupMidfield
        tvDetailAwayForward?.text = data1.strAwayLineupForward
        tvDetailHomeForward?.text = data1.strHomeLineupForward
        tvDetailAwaySubtitutes?.text = data1.strAwayLineupSubstitutes
        tvDetailHomeSubtitutes?.text = data1.strHomeLineupSubstitutes
        presenter.getDetailHomeTeam(data1.idHomeTeam)
        presenter.getDetailAwayTeam(data1.idAwayTeam)
    }

    override fun showDetailHomeTeam(data: List<Team>) {
        val data1 = data[0]
        data1.strTeamBadge?.let { Picasso.get().load(it).into(ivDetailEventHome) }
    }

    override fun showDetailAwayTeam(data: List<Team>) {
        val data1 = data[0]
        data1.strTeamBadge?.let { Picasso.get().load(it).into(ivDetailEventAway) }
    }
}
