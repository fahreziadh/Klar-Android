package id.fahrezi.klar.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fahrezi.klar.view.adapter.viewholder.ListAttendanceViewHolder

class ListAttendanceAdapter() : RecyclerView.Adapter<ListAttendanceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAttendanceViewHolder {
        return ListAttendanceViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: ListAttendanceViewHolder, position: Int) {

    }

}