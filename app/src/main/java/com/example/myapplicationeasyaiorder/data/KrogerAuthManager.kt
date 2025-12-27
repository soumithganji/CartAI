package com.example.myapplicationeasyaiorder.data

import android.content.Context
import android.util.Base64

class KrogerAuthManager(context: Context) {
    private val prefs = context.getSharedPreferences("kroger_auth", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("access_token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("access_token", null)
    }

    fun clearToken() {
        prefs.edit().remove("access_token").apply()
    }

    fun getBasicAuthHeader(clientId: String, clientSecret: String): String {
        val auth = "$clientId:$clientSecret"
        return "Basic " + Base64.encodeToString(auth.toByteArray(), Base64.NO_WRAP)
    }
}
