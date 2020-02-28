package id.fahrezi.klar.service.repository


import android.content.Context
import android.preference.PreferenceManager


class PreferenceHelper(context: Context) {
    companion object {
        val DEVELOP_MODE = false
        private const val ACCESS_TOKEN = "data.source.prefs.ACCESS_TOKEN"
        private const val USER_ID = "data.source.prefs.ID"
        private const val USER_EMAIL = "data.source.prefs.USER_EMAIL"
        private const val USER_FULLNAME = "data.source.prefs.USER_FULLNAME"
        private const val USER_IMAGEPROFILE = "data.source.prefs.USER_IMAGEPROFILE"
        private const val USER_USERNAME = "data.source.prefs.USER_USERNAME"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var accessToken = preferences.getString(ACCESS_TOKEN, "")
        set(value) = preferences.edit().putString(ACCESS_TOKEN, value).apply()

    var userId = preferences.getString(USER_ID, "")
        set(value) = preferences.edit().putString(USER_ID, value).apply()

    var userName = preferences.getString(USER_USERNAME, "")
        set(value) = preferences.edit().putString(USER_USERNAME, value).apply()

    var userFullname = preferences.getString(USER_FULLNAME, "")
        set(value) = preferences.edit().putString(USER_FULLNAME, value).apply()

    var userImageProfile = preferences.getString(USER_IMAGEPROFILE, "")
        set(value) = preferences.edit().putString(USER_IMAGEPROFILE, value).apply()


    var userEmail = preferences.getString(USER_EMAIL, "")
        set(value) = preferences.edit().putString(USER_EMAIL, value).apply()


}