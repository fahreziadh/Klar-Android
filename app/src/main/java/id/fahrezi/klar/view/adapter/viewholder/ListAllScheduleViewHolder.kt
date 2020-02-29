package id.fahrezi.klar.view.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.fahrezi.klar.R
import id.fahrezi.klar.service.model.Response.ScheduleResponse
import id.fahrezi.klar.viewmodel.HomeViewModel
import java.text.SimpleDateFormat

class ListAllScheduleViewHolder(v: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(v.context).inflate(
            R.layout.card_all_schedule,
            v,
            false
        )
    ) {
    private var parentView: LinearLayout
    private var location: TextView
    private var className: TextView
    private var classMajor: TextView
    private var classTime: TextView
    private var classDate: TextView


    init {
        parentView = itemView.findViewById(R.id.parentView)

        location = itemView.findViewById(R.id.location)
        className = itemView.findViewById(R.id.className)
        classMajor = itemView.findViewById(R.id.classMajor)
        classTime = itemView.findViewById(R.id.classTime)
        classDate = itemView.findViewById(R.id.classDate)
    }

    fun bind(model: HomeViewModel, data: ScheduleResponse) {
        //date
        val dateParser = SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss")
        val dateFormatter = SimpleDateFormat("EEEE  dd MMMM yyyy")
        val dateOutput = dateFormatter.format(dateParser.parse(data.startdate))

        //time
        val timeParser = SimpleDateFormat("HH-mm-ss")
        val timeFormatter = SimpleDateFormat("HH:mm")
        val timeOutput = timeFormatter.format(timeParser.parse(data.starttime))

        location.text = data.`class`.location
        className.text = data.`class`.classname
        classMajor.text = data.`class`.major
        classTime.text = timeOutput
        classDate.text = dateOutput

        parentView.setOnClickListener {
            model.navigateToClassDetail.postValue(HomeViewModel.Params(1, data))
        }
    }
}