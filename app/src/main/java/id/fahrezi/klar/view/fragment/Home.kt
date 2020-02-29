package id.fahrezi.klar.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ale.listener.SigninResponseListener
import com.ale.listener.StartResponseListener
import com.ale.rainbowsdk.RainbowSdk
import id.fahrezi.klar.R
import id.fahrezi.klar.service.model.Response.ClassResponse
import id.fahrezi.klar.service.model.Response.ScheduleResponse
import id.fahrezi.klar.service.repository.PreferenceHelper
import id.fahrezi.klar.view.adapter.ListClassAdapter
import id.fahrezi.klar.view.adapter.ListScheduleAdapter
import id.fahrezi.klar.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nothing_class.*
import kotlinx.android.synthetic.main.schedule.*
import kotlinx.android.synthetic.main.update.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text


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

    fun checkAuth() {
        var accessToken = PreferenceHelper(context!!).accessToken
        if (accessToken == "") {
            findNavController().navigate(R.id.auth)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = PreferenceHelper(context!!)
        checkAuth()
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        fullname.text = pref.userFullname
        create_class.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_createClass)
        }
        showClass()
        showSchedule()
        checkProfile()
        joinClass()
    }

    fun joinClass() {
        imageView2.setOnClickListener {
            var dialog = AlertDialog.Builder(context!!)
            var v = LayoutInflater.from(context).inflate(R.layout.nothing_class, null, false)
            var layout = v.findViewById<ConstraintLayout>(R.id.nothing_class)
            layout.visibility = View.VISIBLE
            var joinBtn = v.findViewById<TextView>(R.id.joinBtn)
            var joinBox = v.findViewById<EditText>(R.id.joinBox)
            joinBtn.setOnClickListener {
                var classId = joinBox.text.toString()
                viewModel.joinClass(classId, pref.accessToken!!)
                joinBox.isEnabled=false
                joinBtn.text="wait..."
                joinBtn.isEnabled=false
            }
            viewModel.joinClass.observe(viewLifecycleOwner, Observer {
                if (it){
                    Toast.makeText(context!!,"Success to Join",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context!!,"Failed to Join",Toast.LENGTH_SHORT).show()
                }
                joinBox.isEnabled=true
                joinBtn.text="Join"
                joinBtn.isEnabled=true
            })
            dialog.setView(v)
            dialog.create().show()
        }
        joinBtn.setOnClickListener {
            var classId = joinBox.text.toString()
            viewModel.joinClass(classId, pref.accessToken!!)
            joinBox.isEnabled=false
            joinBtn.text="wait..."
            joinBtn.isEnabled=false
        }
        viewModel.joinClass.observe(viewLifecycleOwner, Observer {
            if (it){
                Toast.makeText(context!!,"Success to Join",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context!!,"Failed to Join",Toast.LENGTH_SHORT).show()
            }
            joinBox.isEnabled=true
            joinBtn.text="Join"
            joinBtn.isEnabled=true
        })

    }

    fun showClass() {
        var layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        var list = ArrayList<ClassResponse>()
        layoutManager.isSmoothScrollbarEnabled = true
        var adapter = ListClassAdapter(viewModel, list)
        list_class.adapter = adapter
        list_class.layoutManager = layoutManager


        viewModel.showListClass(pref.accessToken!!)
        viewModel.listClass.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
                have_class.visibility = View.VISIBLE
                nothing_class.visibility = View.GONE
            } else {
                nothing_class.visibility = View.VISIBLE
                have_class.visibility = View.GONE
            }
        })
    }

    fun showSchedule() {
        var listSchedule = ArrayList<ScheduleResponse>()
        var scheduleAdapter = ListScheduleAdapter(viewModel, listSchedule)
        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        layoutManager.isSmoothScrollbarEnabled = true
        list_schedule.isNestedScrollingEnabled = false
        list_schedule.layoutManager = layoutManager
        list_schedule.adapter = scheduleAdapter
        viewModel.navigateToClassDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                var bundle = bundleOf("bundle" to it.s)
                this.findNavController().navigate(R.id.action_allSchedule_to_classDetail, bundle)
                viewModel.navigateToClassDetail.value = null
            }
        })
        viewModel.showListSchedulle("true", pref.accessToken!!)
        viewModel.listSchedule.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                listSchedule.clear()
                listSchedule.addAll(it)
                scheduleAdapter.notifyDataSetChanged()
                no_schedule.visibility = View.GONE
            } else {
                no_schedule.visibility = View.VISIBLE
            }
        })

        allSchedule.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_allSchedule)
        }
        allSchedule2.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_allSchedule)
        }
    }

    fun checkProfile() {
        if (pref.accessToken != "" && pref.userFullname == "") {
            findNavController().navigate(R.id.action_home_to_changeProfile)
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
