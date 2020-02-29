package id.fahrezi.klar

import android.app.Application
import com.ale.rainbowsdk.RainbowSdk

class Klar:Application(){
    override fun onCreate() {
        super.onCreate()
        RainbowSdk.instance().initialize(this, "2da85080567111eaa237f3c9eaf4b758", "zjuNsLWIzfzKgAsW1q9lAvBMucTkckAwxi8YZyGjV30H1MOopOqyGBa0QhTr1ArK")
    }
}