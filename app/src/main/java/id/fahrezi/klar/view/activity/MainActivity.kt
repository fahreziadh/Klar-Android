package id.fahrezi.klar.view.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.fahrezi.klar.R
import id.fahrezi.klar.view.fragment.*

class MainActivity : AppCompatActivity(), Auth.OnFragmentInteractionListener,
    Home.OnFragmentInteractionListener, CreateClass.OnFragmentInteractionListener,
    ClassDetail.OnFragmentInteractionListener, Attendance.OnFragmentInteractionListener,
    ChangeProfile.OnFragmentInteractionListener, AllClass.OnFragmentInteractionListener,
    AllSchedule.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
