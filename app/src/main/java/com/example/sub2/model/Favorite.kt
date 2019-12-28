package com.example.sub2.model

data class Favorite (val id: Long?, val eventId: String?, val teamHome: String?, val teamAway: String?, val scoreHome: String?, val scoreAway: String?, val eventDate: String?, val event:String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val TEAM_HOME: String = "TEAM_HOME"
        const val TEAM_AWAY: String = "TEAM_AWAY"
        const val SCORE_HOME: String = "SCORE_HOME"
        const val SCORE_AWAY: String = "SCORE_AWAY"
        const val DATE: String = "DATE"
        const val EVENT: String = "EVENT"
    }
}