package com.example.sub2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
	val intHomeShots: String? = null,
	val strSport: String? = null,
	val strHomeLineupDefense: String? = null,
	val strAwayLineupSubstitutes: String? = null,
	val strTweet1: String? = null,
	val strTweet2: String? = null,
	val strTweet3: String? = null,
	val idLeague: String? = null,
	val idSoccerXML: String? = null,
	val strHomeLineupForward: String? = null,
	val strTVStation: String? = null,
	val strHomeGoalDetails: String? = null,
	val strVideo: String? = null,
	val strAwayLineupGoalkeeper: String? = null,
	val strAwayLineupMidfield: String? = null,
	val idEvent: String? = null,
	val intRound: String? = null,
	val strHomeYellowCards: String? = null,
	val idHomeTeam: String? = null,
	val intHomeScore: String? = null,
	val dateEvent: String? = null,
	val strCountry: String? = null,
	val strAwayTeam: String? = null,
	val strHomeLineupMidfield: String? = null,
	val strDate: String? = null,
	val strHomeFormation: String? = null,
	val strMap: String? = null,
	val idAwayTeam: String? = null,
	val strAwayRedCards: String? = null,
	val strBanner: String? = null,
	val strFanart: String? = null,
	val strDescriptionEN: String? = null,
	val dateEventLocal: String? = null,
	val strResult: String? = null,
	val strCircuit: String? = null,
	val intAwayShots: String? = null,
	val strFilename: String? = null,
	val strTime: String? = null,
	val strAwayGoalDetails: String? = null,
	val strAwayLineupForward: String? = null,
	val strTimeLocal: String? = null,
	val strLocked: String? = null,
	val strSeason: String? = null,
	val intSpectators: String? = null,
	val strEventAlternate: String? = null,
	val strHomeRedCards: String? = null,
	val strHomeLineupGoalkeeper: String? = null,
	val strHomeLineupSubstitutes: String? = null,
	val strAwayFormation: String? = null,
	val strEvent: String? = null,
	val strAwayYellowCards: String? = null,
	val strAwayLineupDefense: String? = null,
	val strHomeTeam: String? = null,
	val strThumb: String? = null,
	val strLeague: String? = null,
	val intAwayScore: String? = null,
	val strCity: String? = null,
	val strPoster: String? = null
) : Parcelable
