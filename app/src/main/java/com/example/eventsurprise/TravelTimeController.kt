package com.example.eventsurprise

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import java.util.*

private val X_APPLICATION_ID = ""
private val X_API_KEY = ""

private data class DepartureSearch(
    val id: String,
    val coordinates: LatLng,
    val transportation: String,
    val departureTime: Date,
    val travelTime: Number
)

data class Coordinate(
    val lat: Double,
    val lng: Double
)

data class Shape(
    val shell: List<Coordinate>,
    val holes: List<Coordinate>
)

data class Result(
    val search_id: String,
    val shapes: List<Shape>
)

data class ResultList(
    val results: List<Result>
) {
    class Deserializer : ResponseDeserializable<ResultList> {
        override fun deserialize(content: String) = Gson().fromJson(content, ResultList::class.java)
    }
}

fun getTimeMap(coordinates: LatLng): ResultList {
    Fuel.post("https://api.traveltimeapp.com/v4/time-map")
        .header(Headers.CONTENT_TYPE, "application/json")
        .header("X-Application-Id", X_APPLICATION_ID)
        .header("X-Api-Key", X_API_KEY)
        .header(Headers.ACCEPT, "application/json")
        .body("{\n" +
                "  \"departure_searches\": [\n" +
                "    {\n" +
                "      \"id\": \"public transport from Trafalgar Square\",\n" +
                "      \"coords\": {\n" +
                "        \"lat\": 51.507609,\n" +
                "        \"lng\": -0.128315\n" +
                "      },\n" +
                "      \"transportation\": {\n" +
                "        \"type\": \"public_transport\"\n" +
                "      },\n" +
                "      \"departure_time\": \"2019-09-27T08:00:00Z\",\n" +
                "      \"travel_time\": 900\n" +
                "    }\n" +
                "  ]\n" +
                "}")
        .responseObject(ResultList.Deserializer()) { result ->
             return result.get()
        }
}
