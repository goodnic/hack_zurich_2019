package com.example.eventsurprise

import com.github.kittinunf.fuel.httpGet
import io.data2viz.geojson.FeatureCollection
import io.data2viz.geojson.toGeoJsonObject

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

fun getPOIs(borderCoordinates: List<Coordinate>): FeatureCollection {
    val boundingBox = extractFromCoordinateList(borderCoordinates)
    val (_, _, result) = "https://api.opentripmap.com/0.1/en/places/bbox".httpGet(
        listOf(
            "lang" to "en",
            "lon_min" to boundingBox.minLng.toString(),
            "lon_max" to boundingBox.maxLng.toString(),
            "lat_min" to boundingBox.minLat.toString(),
            "lat_max" to boundingBox.maxLat.toString(),
            "apikey" to ""
        )
    ).response()
    return result.toString().toGeoJsonObject() as FeatureCollection
}
