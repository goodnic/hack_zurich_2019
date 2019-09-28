package com.example.eventsurprise

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.google.android.gms.maps.model.LatLng
import java.util.*

private val X_APPLICATION_ID = "af8183df"
private val X_API_KEY = "21f500db6a3e53d627b0b5adf67e4847"

private data class DepartureSearch(
    val id: String,
    val coordinates: LatLng,
    val transportation: String,
    val departureTime: Date,
    val travelTime: Number
)

fun getTimeMap(coordinates: LatLng) {
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
        .response { result ->
            print(result)
        }
}
