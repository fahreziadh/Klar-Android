package id.fahrezi.klar.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fahrezi.klar.view.adapter.viewholder.ListStudentViewHolderBadge

class ListStudentAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListStudentViewHolderBadge(parent)
    }

    override fun getItemCount(): Int {
       return 5
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

}