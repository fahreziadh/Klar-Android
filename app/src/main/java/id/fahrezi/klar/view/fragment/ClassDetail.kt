package id.fahrezi.klar.view.fragment

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.fahrezi.klar.R
import id.fahrezi.klar.service.model.Response.ScheduleResponse
import id.fahrezi.klar.service.repository.PreferenceHelper
import id.fahrezi.klar.view.adapter.ListChatAdapter
import id.fahrezi.klar.view.adapter.ListStudentAdapter
import kotlinx.android.synthetic.main.detail_section_1.*
import kotlinx.android.synthetic.main.detail_section_2.*
import kotlinx.android.synthetic.main.fragment_class_detail.*
import java.text.SimpleDateFormat


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ClassDetail : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var schedule: ScheduleResponse
    lateinit var pref: PreferenceHelper


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
        schedule = arguments?.getParcelable("bundle")!!
        pref = PreferenceHelper(context!!)
        init()
        back.setOnClickListener {
            findNavController().navigateUp()
        }
        attendance.setOnClickListener {
            var bottomSheetFragment = Attendance.newInstance(schedule.id, schedule.`class`.classid,startDate.text.toString())
            bottomSheetFragment?.show(parentFragmentManager, bottomSheetFragment?.tag)
        }
        changeSchedule.setOnClickListener {
            findNavController().navigate(R.id.action_classDetail_to_allSchedule)
        }
        setUpStudents()
        setUpChat()
    }

    fun init() {
        className.text = schedule.`class`.classname
        classId.text = "Class ID : #" + schedule.`class`.classid
        classGrade.text = schedule.`class`.grade
        classMajor.text = schedule.`class`.major
        classLocation.text = schedule.`class`.location

        //date
        val dateParser = SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss")
        val dateFormatter = SimpleDateFormat("EEEE  dd MMMM yyyy")
        val dateOutput = dateFormatter.format(dateParser.parse(schedule.startdate))

        //time
        val timeParser = SimpleDateFormat("HH-mm-ss")
        val timeFormatter = SimpleDateFormat("HH:mm")
        val timeOutput = timeFormatter.format(timeParser.parse(schedule.starttime))

        startTime.text = timeOutput
        startDate.text = dateOutput

        //check if owner
        if (schedule.`class`.owner.username == pref.userName) {
            attendance.visibility = View.VISIBLE
        } else {
            attendance.visibility = View.GONE
        }

        //Teacher set
        teacher.text = schedule.`class`.owner.username
        if (schedule.`class`.owner.fullname != "") {
            teacher.text = schedule.`class`.owner.fullname
        }
//        teacherImageProfile.setBackgroundColor(Color.parseColor(schedule.`class`.owner.imageprofile))
//        teacherInitial.text = teacher.text.toString()[0].toString().toUpperCase()

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
