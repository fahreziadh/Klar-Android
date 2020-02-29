package id.fahrezi.klar.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import id.fahrezi.klar.R
import id.fahrezi.klar.service.model.Response.ScheduleResponse
import id.fahrezi.klar.service.repository.PreferenceHelper
import id.fahrezi.klar.view.adapter.ListAllSchedule
import id.fahrezi.klar.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_all_schedule.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AllSchedule : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var model: HomeViewModel

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
        return inflater.inflate(R.layout.fragment_all_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        setUp()
        back.setOnClickListener {
            findNavController().navigateUp()
        }
        model.navigateToClassDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                var bundle = bundleOf("bundle" to it.s)
                this.findNavController().navigate(R.id.action_allSchedule_to_classDetail, bundle)
                model.navigateToClassDetail.value = null
            }
        })
    }

    fun setUp() {
        var list = ArrayList<ScheduleResponse>()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        layoutManager.isSmoothScrollbarEnabled = true
        list_schedule.layoutManager = layoutManager
        var adapter = ListAllSchedule(model, list)
        list_schedule.adapter = adapter
        model.showListSchedulle("false", PreferenceHelper(context!!).accessToken!!)
        model.listSchedule.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                list.addAll(it)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Nothing Schedule", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
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
            AllSchedule().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
