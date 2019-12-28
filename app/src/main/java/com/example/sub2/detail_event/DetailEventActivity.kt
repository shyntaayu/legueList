package com.example.sub2.detail_event

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.sub2.R
import com.example.sub2.R.menu.*
import com.example.sub2.R.drawable.*
import com.example.sub2.R.id.*
import com.example.sub2.api.ApiRepository
import com.example.sub2.db.database
import com.example.sub2.model.Event
import com.example.sub2.model.Favorite
import com.example.sub2.model.Team
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_deatil_event.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.design.snackbar
import java.text.SimpleDateFormat
import java.util.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.delete

class DetailEventActivity : AppCompatActivity(), DetailEventView {
    private lateinit var presenter: DetailEventPresenter
    private lateinit var eventId: String
    private lateinit var eventName: String
    private lateinit var events: Event
    private lateinit var eventType: String
    private var isFavorite: Boolean = false
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventId = intent.getStringExtra("eventId")
        eventType = intent.getStringExtra("eventType")
        eventName = intent.getStringExtra("eventName")
        setContentView(R.layout.activity_deatil_event)
        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailEventPresenter(this, request, gson)

        supportActionBar?.title = eventName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


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
        events = data[0]

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_ID to events.idEvent,
                    Favorite.TEAM_HOME to events.strHomeTeam,
                    Favorite.TEAM_AWAY to events.strAwayTeam,
                    Favorite.SCORE_HOME to events.intHomeScore,
                    Favorite.SCORE_AWAY to events.intAwayScore,
                    Favorite.DATE to events.dateEvent,
                    Favorite.EVENT to eventType
                    )
            }
            swipeRefreshEvent.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefreshEvent.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to eventId)
            }
            swipeRefreshEvent.snackbar("Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefreshEvent.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(EVENT_ID = {id})",
                    "id" to eventId)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}
