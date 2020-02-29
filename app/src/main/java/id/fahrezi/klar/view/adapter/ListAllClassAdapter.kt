package id.fahrezi.klar.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fahrezi.klar.view.adapter.viewholder.ListClassViewHolder

class ListAllClassAdapter() : RecyclerView.Adapter<ListClassViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListClassViewHolder {
        return ListClassViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: ListClassViewHolder, position: Int) {

    }

}
