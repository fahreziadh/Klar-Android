package id.fahrezi.klar.view.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fahrezi.klar.R

class ListChatViewHolder(var parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.card_chat, parent, false
    )
) {
    init {

    }
}