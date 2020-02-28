package id.fahrezi.klar.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fahrezi.klar.view.adapter.viewholder.ListChatViewHolder

class ListChatAdapter() : RecyclerView.Adapter<ListChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListChatViewHolder {
        return ListChatViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ListChatViewHolder, position: Int) {

    }

}