package id.fahrezi.klar.view.adapter

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import id.fahrezi.klar.service.model.Response.AttendanceResponse
import id.fahrezi.klar.service.model.Response.StudentResponse
import id.fahrezi.klar.view.adapter.viewholder.ListAttendanceViewHolder
import id.fahrezi.klar.viewmodel.AttendanceViewModel

class ListAttendanceAdapter(
    val attendance: ArrayList<AttendanceResponse>,
    val student: ArrayList<StudentResponse>,
    val model: AttendanceViewModel,
    val scheduleId: String,
    val viewLifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<ListAttendanceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAttendanceViewHolder {
        return ListAttendanceViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return student.size
    }

    override fun onBindViewHolder(holder: ListAttendanceViewHolder, position: Int) {
        holder.bind(student.get(position), attendance,model,scheduleId,viewLifecycleOwner)
    }

}