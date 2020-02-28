package id.fahrezi.klar.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fahrezi.klar.view.adapter.viewholder.ListScheduleViewHolder
import id.fahrezi.klar.viewmodel.HomeViewModel

class ListScheduleAdapter(private var viewModel: HomeViewModel) :
    RecyclerView.Adapter<ListScheduleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListScheduleViewHolder {

        return ListScheduleViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return 50
    }

    override fun onBindViewHolder(holder: ListScheduleViewHolder, position: Int) {
        holder.bind(viewModel)
    }
}