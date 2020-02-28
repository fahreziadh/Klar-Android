package id.fahrezi.klar.view.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.fahrezi.klar.R
import id.fahrezi.klar.viewmodel.HomeViewModel

class ListScheduleViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.card_schedule, parent, false
    )
) {
    var parentView: LinearLayout

    init {
        parentView = itemView.findViewById(R.id.parentView)
    }

    fun bind(viewModel: HomeViewModel) {
        parentView.setOnClickListener {
            viewModel.navigateToClassDetail.postValue(1)
        }
    }
}