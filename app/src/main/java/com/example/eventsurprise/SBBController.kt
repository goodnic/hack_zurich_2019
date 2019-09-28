package com.example.eventsurprise

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.awaitResponseResult
import com.github.kittinunf.fuel.coroutines.awaitObjectResponse
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import java.util.*

private const val X_Contract_Id = "ABC1234"
private const val X_Conversation_Id = "cafebabe-0815-4711-1234-ffffdeadffff" //TODO: make this unique per user!

fun getLocationData(accessToken : String) : String {
    val (_, _, result) = "https://b2p-int.api.sbb.ch/api/locations/?name=Bern".httpGet()
        .header(Headers.ACCEPT, "application/json")
        .header("Authorization", "Bearer $accessToken")
        .header("Cache-Control", "no-cache")
        .header("X-Contract-Id", X_Contract_Id)
        .header("X-Conversation-Id", X_Conversation_Id)
        .also { Log.d("MAP", "getLocationData URL: " + it.url + "; Authorization: " + it.headers.get("Authorization")) }
        .responseString()
    return result.get()
}

data class AccessField(
    val field: String,
    val access_token: String
)

fun getAuthToken() : String {
    val (_, _, result) = "https://sso-int.sbb.ch/auth/realms/SBB_Public/protocol/openid-connect/token".httpPost(
        listOf(
            "grant_type" to "client_credentials",
            "client_id" to "22ebc2be",
            "client_secret" to "2c820784f3e28837959abc43120989ca"
        )
    ).responseString()
    val g = Gson()
    val accessField = g.fromJson(result.get(), AccessField::class.java)
    return accessField.access_token
}
