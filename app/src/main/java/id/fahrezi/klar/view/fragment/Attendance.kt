package id.fahrezi.klar.view.fragment

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.fahrezi.klar.view.adapter.ListAttendanceAdapter
import id.fahrezi.klar.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.fahrezi.klar.service.model.Response.AttendanceResponse
import id.fahrezi.klar.service.model.Response.StudentResponse
import id.fahrezi.klar.service.repository.PreferenceHelper
import id.fahrezi.klar.viewmodel.AttendanceViewModel
import kotlinx.android.synthetic.main.fragment_attendance.*

private const val ID_SCHEDULE = "param1"
private const val ID_CLASS = "param2"
private const val DATE = "param3"

class Attendance : BottomSheetDialogFragment() {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var dialog: BottomSheetDialog
    private lateinit var behavior: BottomSheetBehavior<View>
    private lateinit var model: AttendanceViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val d = it as BottomSheetDialog
            val sheet = d.findViewById<View>(R.id.design_bottom_sheet)
            behavior = BottomSheetBehavior.from(sheet)
            behavior.isHideable = true
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_attendance, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(this).get(AttendanceViewModel::class.java)
        var scheduleId = arguments!!.getString(ID_SCHEDULE)
        var classId = arguments!!.getString(ID_CLASS)
        var dates = arguments!!.getString(DATE)
        date.text = dates
        setUpAttendance(classId, scheduleId)

    }


    fun setUpAttendance(classId: String?, scheduleId: String?) {
        var pref = PreferenceHelper(context!!)
        var attendance = ArrayList<AttendanceResponse>()
        var student = ArrayList<StudentResponse>()
        model.getListStudentAndAttendance(classId!!, scheduleId!!, pref.accessToken!!)
        var layoutManager = GridLayoutManager(context!!, 2, GridLayoutManager.VERTICAL, false)
        layoutManager.isSmoothScrollbarEnabled = true
        var adapter =
            ListAttendanceAdapter(attendance, student, model, scheduleId, viewLifecycleOwner)
        list_attendance.layoutManager = layoutManager
        list_attendance.adapter = adapter

        model.listStudent.observe(viewLifecycleOwner, Observer {
            if (it.student != null) {
                student.addAll(it.student!!)
                if (it.attendance != null) {
                    attendance.addAll(it.attendance!!)
                }
            } else {
                Toast.makeText(context, "Invite some Student", Toast.LENGTH_SHORT).show()
            }
            adapter.notifyDataSetChanged()
        })


    }

    override fun onStart() {
        super.onStart()
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onResume() {
        super.onResume()
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
        fun newInstance(id: String, classId: String, date: String) =
            Attendance().apply {
                arguments = Bundle().apply {
                    putString(ID_SCHEDULE, id)
                    putString(ID_CLASS, classId)
                    putString(DATE, date)
                }
            }
    }
}
