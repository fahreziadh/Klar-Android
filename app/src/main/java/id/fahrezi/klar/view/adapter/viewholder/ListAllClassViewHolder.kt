package id.fahrezi.klar.view.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fahrezi.klar.R

class ListAllClassViewHolder(v: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(v.context).inflate(
            R.layout.card_all_schedule,
            v,
            false
        )
    )