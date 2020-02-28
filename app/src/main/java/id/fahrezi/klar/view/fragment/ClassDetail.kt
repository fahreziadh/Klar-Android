package id.fahrezi.klar.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.fahrezi.klar.view.adapter.ListChatAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.fahrezi.klar.R
import kotlinx.android.synthetic.main.detail_section_1.*
import id.fahrezi.klar.view.adapter.ListStudentAdapter
import kotlinx.android.synthetic.main.detail_section_2.*
import kotlinx.android.synthetic.main.fragment_class_detail.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ClassDetail : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

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
        return inflater.inflate(R.layout.fragment_class_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back.setOnClickListener {
            findNavController().navigateUp()
        }
        attendance.setOnClickListener {
            //            findNavController().navigate(R.id.action_classDetail_to_attendance)
            var bottomSheetFragment = Attendance.newInstance()
            bottomSheetFragment?.show(parentFragmentManager, bottomSheetFragment?.tag)
        }
        setUpStudents()
        setUpChat()
    }

    fun setUpStudents() {
        var layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        layoutManager.isSmoothScrollbarEnabled = true
        var adapter = ListStudentAdapter()
        list_students.layoutManager = layoutManager
        list_students.adapter = adapter
    }

    fun setUpChat() {
        var layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, true)
        layoutManager.isSmoothScrollbarEnabled = true
        var adapter = ListChatAdapter()
        list_chat.layoutManager = layoutManager
        list_chat.adapter = adapter
        list_chat.isNestedScrollingEnabled = false
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
            ClassDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
