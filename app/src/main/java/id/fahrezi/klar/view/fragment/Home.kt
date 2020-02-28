package id.fahrezi.klar.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.fahrezi.klar.view.adapter.ListScheduleAdapter

import id.fahrezi.klar.R
import id.fahrezi.klar.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.nothing_class.*
import kotlinx.android.synthetic.main.schedule.*
import id.fahrezi.klar.service.repository.PreferenceHelper
import id.fahrezi.klar.service.model.Request.ChangeProfileRequest
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.random.Random

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var pref: PreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = PreferenceHelper(context!!)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        checkProfile()
        create_class.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_createClass)
        }
        showSchedule()
    }

    fun showSchedule() {
        var scheduleAdapter = ListScheduleAdapter(viewModel)
        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        layoutManager.isSmoothScrollbarEnabled = true
        list_schedule.isNestedScrollingEnabled = false
        list_schedule.layoutManager = layoutManager
        list_schedule.adapter = scheduleAdapter
        viewModel.navigateToClassDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(R.id.action_home_to_classDetail)
                viewModel.navigateToClassDetail.value = null
            }
        })
    }

    fun checkProfile() {
        if (pref.userFullname == "") {
            var alert = android.app.AlertDialog.Builder(context!!)
            var v =
                LayoutInflater.from(context!!).inflate(R.layout.dialog_profile_change, null, false)
            var boxName = v.findViewById<EditText>(R.id.boxName)
            var saveButton = v.findViewById<TextView>(R.id.saveButton)
            var show = alert.create()
            saveButton.setOnClickListener {
                var color = arrayOf(
                    "#ff9ff3",
                    "#feca57",
                    "#ff6b6b",
                    "#48dbfb",
                    "#1dd1a1",
                    "#f368e0",
                    "#ff9f43",
                    "#ff9f43",
                    "#ee5253",
                    "#0abde3",
                    "#10ac84",
                    "#00d2d3",
                    "#54a0ff",
                    "#5f27cd",
                    "#5f27cd",
                    "##576574",
                    "#01a3a4",
                    "#2e86de",
                    "#222f3e"
                )
                var rand = Random.nextInt(0, color.size)
                viewModel.changeProfile(
                    ChangeProfileRequest(boxName.text.toString(), color[rand]),
                    pref.accessToken!!
                )
                viewModel.changeProfile.observe(viewLifecycleOwner, Observer {
                    if (it) {
                        fullname.text = boxName.text.toString()
                    }
                    show.dismiss()
                })
            }
            show.show()


        }
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener") as Throwable
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
