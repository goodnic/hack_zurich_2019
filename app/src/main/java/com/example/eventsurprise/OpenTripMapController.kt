package com.example.eventsurprise

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson

data class PointOfInterest(
    val xid: String,
    val rate: Number,
    val name: String,
    val osm: String,
    val kinds: String,
    val wikidata: String,
    val point: LatLon
) {
    class Deserializer : ResponseDeserializable<Array<PointOfInterest>> {
        override fun deserialize(content: String) = Gson().fromJson(content, Array<PointOfInterest>::class.java)
    }
}

data class LatLon(
    val lon: Double,
    val lat: Double
)

private data class BoundingBox(
    val minLng: Double,
    val maxLng: Double,
    val minLat: Double,
    val maxLat: Double
)

private fun extractFromCoordinateList(coordinateList: List<Coordinate>): BoundingBox {
    return BoundingBox(
        coordinateList.minBy { it.lng }!!.lng,
        coordinateList.maxBy { it.lng }!!.lng,
        coordinateList.minBy { it.lat }!!.lat,
        coordinateList.maxBy { it.lat }!!.lat
    )
}

fun getPOIs(borderCoordinates: List<Coordinate>): Array<PointOfInterest> {
    val boundingBox = extractFromCoordinateList(borderCoordinates)
    val (_, _, result) = "https://api.opentripmap.com/0.1/en/places/bbox".httpGet(
        listOf(
            "lang" to "en",
            "lon_min" to boundingBox.minLng.toString(),
            "lon_max" to boundingBox.maxLng.toString(),
            "lat_min" to boundingBox.minLat.toString(),
            "lat_max" to boundingBox.maxLat.toString(),
            "apikey" to "",
            "format" to "json"
        )
    ).responseObject(PointOfInterest.Deserializer())
    return result.get()
}
