package id.fahrezi.klar.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fahrezi.klar.service.model.Response.ScheduleResponse
import id.fahrezi.klar.view.adapter.viewholder.ListAllScheduleViewHolder
import id.fahrezi.klar.viewmodel.HomeViewModel

class ListAllSchedule(
    val model: HomeViewModel,
    val list: ArrayList<ScheduleResponse>
) : RecyclerView.Adapter<ListAllScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAllScheduleViewHolder {
        return ListAllScheduleViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListAllScheduleViewHolder, position: Int) {
        holder.bind(model,list.get(position))
    }

}