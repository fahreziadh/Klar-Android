package id.fahrezi.klar.view.adapter.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import id.fahrezi.klar.R
import id.fahrezi.klar.service.model.Request.AttendanceRequest
import id.fahrezi.klar.service.model.Response.AttendanceResponse
import id.fahrezi.klar.service.model.Response.StudentResponse
import id.fahrezi.klar.service.repository.PreferenceHelper
import id.fahrezi.klar.viewmodel.AttendanceViewModel

class ListAttendanceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.card_attendance, parent, false)
) {
    var studentName: TextView
    var checklist: ImageView
    var itemClick: LinearLayout
    var attStatus = false
    lateinit var alert: AlertDialog

    init {
        studentName = itemView.findViewById(R.id.studentName)
        checklist = itemView.findViewById(R.id.checklist)
        itemClick = itemView.findViewById(R.id.itemClick)
    }

    fun bind(
        student: StudentResponse,
        attendance: ArrayList<AttendanceResponse>,
        model: AttendanceViewModel,
        scheduleId: String,
        viewLifecycleOwner: LifecycleOwner
    ) {
        studentName.text = student.fullname
        attendance.map {
            if (it.student == student) {
                if (it.attendance) {
                    attStatus = true
                    checklist.visibility = View.VISIBLE
                } else {
                    attStatus = false
                    checklist.visibility = View.GONE
                }
            }
        }
        itemClick.setOnClickListener {
            model.inputAttendance(
                PreferenceHelper(itemView.context).accessToken!!,
                AttendanceRequest(!attStatus, scheduleId, student)
            )
            showMessage(itemView.context)
        }
        model.inputAttendance.observe(viewLifecycleOwner, Observer {
            if (it) {
                attStatus = !attStatus
                if (attStatus) {
                    checklist.visibility = View.VISIBLE
                } else {
                    checklist.visibility = View.GONE
                }
            } else {
            }
            alert.dismiss()
        })
    }

    fun showMessage(context: Context) {
        var dialog = AlertDialog.Builder(context!!)
        var v = LayoutInflater.from(context).inflate(R.layout.dialog_message, null, false)
        var massage = v.findViewById<TextView>(R.id.message)
        var anim = v.findViewById<LottieAnimationView>(R.id.animation_view)

        massage.text = "Please wait...."
        anim.setAnimation(R.raw.loading_profile)
        anim.loop(true)
        anim.playAnimation()

        dialog.setView(v)
        alert = dialog.create()
        alert.show()
    }
}