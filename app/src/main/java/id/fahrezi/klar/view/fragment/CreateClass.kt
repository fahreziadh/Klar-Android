package id.fahrezi.klar.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView

import id.fahrezi.klar.R
import id.fahrezi.klar.service.model.Request.ClassRequest
import id.fahrezi.klar.service.model.Request.Detailtime
import id.fahrezi.klar.service.model.Request.ScheduleRequest
import id.fahrezi.klar.service.repository.PreferenceHelper
import kotlinx.android.synthetic.main.fragment_create_class.*
import kotlinx.android.synthetic.main.fragment_home.*
import id.fahrezi.klar.service.model.Request.Owner
import id.fahrezi.klar.service.model.Response.ClassResponse
import id.fahrezi.klar.viewmodel.ClassViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreateClass : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var month = 0

    lateinit var model: ClassViewModel
    var LOADING = 1
    var ERROR = 2
    var SUCCESS = 3

    lateinit var alert: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_class, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProviders.of(this).get(ClassViewModel::class.java)
        back.setOnClickListener {
            findNavController().navigateUp()
        }
        setLongTime()
        setDetailTime()
        setUp()
    }

    fun setDetailTime() {
        sunday.setOnClickListener { showClock(sunday, sundayRemove) }
        monday.setOnClickListener { showClock(monday, mondayRemove) }
        tuesday.setOnClickListener { showClock(tuesday, tuesdayRemove) }
        wednesday.setOnClickListener { showClock(wednesday, wednesdayRemove) }
        thursday.setOnClickListener { showClock(thursday, thursdayRemove) }
        friday.setOnClickListener { showClock(friday, fridayRemove) }
        saturday.setOnClickListener { showClock(saturday, saturdayRemove) }

        sundayRemove.setOnClickListener { sundayRemove.text = ""; sunday.text = "00:00" }
        mondayRemove.setOnClickListener { mondayRemove.text = ""; monday.text = "00:00" }
        tuesdayRemove.setOnClickListener { tuesdayRemove.text = ""; tuesday.text = "00:00" }
        wednesdayRemove.setOnClickListener { wednesdayRemove.text = ""; wednesday.text = "00:00" }
        thursdayRemove.setOnClickListener { thursdayRemove.text = ""; thursday.text = "00:00" }
        fridayRemove.setOnClickListener { fridayRemove.text = ""; friday.text = "00:00" }
        saturdayRemove.setOnClickListener { saturdayRemove.text = ""; saturday.text = "00:00" }
    }

    fun showClock(textView: TextView, remove: TextView) {
        var alertDialog = AlertDialog.Builder(context!!)
        var v = LayoutInflater.from(context).inflate(R.layout.dialog_clock, null, false)
        var timePicker = v.findViewById<TimePicker>(R.id.timePicker)
        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour
            var am_pm = ""
            when {
                hour == 0 -> {
                    hour += 12
                    am_pm = "AM"
                }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            textView.text = hour.toString() + " " + minute + " " + am_pm
        }
        remove.text = "X"
        alertDialog.setView(v)
        alertDialog.create().show()
    }

    fun setLongTime() {
        onemonth.setOnClickListener {
            month = 1
            onemonthcard.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
            threemonthcard.setCardBackgroundColor(resources.getColor(R.color.white))
            sixmonthcard.setCardBackgroundColor(resources.getColor(R.color.white))
        }

        threemonth.setOnClickListener {
            month = 3
            onemonthcard.setCardBackgroundColor(resources.getColor(R.color.white))
            threemonthcard.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
            sixmonthcard.setCardBackgroundColor(resources.getColor(R.color.white))
        }
        sixmonth.setOnClickListener {
            month = 6
            onemonthcard.setCardBackgroundColor(resources.getColor(R.color.white))
            threemonthcard.setCardBackgroundColor(resources.getColor(R.color.white))
            sixmonthcard.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))

        }
    }

    fun setUp() {
        var pref = PreferenceHelper(context!!)
        create_class.setOnClickListener {
            var className = className.text.toString()
            var classMajor = classMajor.text.toString()
            var classGrade = classGrade.text.toString()
            var classLocation = classLocation.text.toString()
            var classDetailLocation = classDetailLocation.text.toString()
            var owner = Owner(pref.userFullname!!, pref.userImageProfile!!, pref.userName!!)

            var Sunday = sunday.text.toString()
            var Monday = monday.text.toString()
            var Tuesday = tuesday.text.toString()
            var Wednesday = wednesday.text.toString()
            var Thursday = thursday.text.toString()
            var Friday = friday.text.toString()
            var Saturday = saturday.text.toString()

            var totalMonth = month
            var setMonth = (1 + startDate.month)
            var fixMonth = ""
            if (setMonth < 10) {
                fixMonth = ("0") + setMonth.toString()
            }
            var status = "active"
//            var scheduleRequest = ScheduleRequest()
            var startdate =
                startDate.year.toString() + "-" + fixMonth + "-" + startDate.dayOfMonth.toString()
            if (startdate == "" || month == 0 || className == "" || classMajor == "" || classGrade == "" || classLocation == "" || classDetailLocation == "") {
                Toast.makeText(context, "Please complete all sections", Toast.LENGTH_SHORT).show()
            } else {
                if (sunday.text == "00:00" && monday.text == "00:00" && tuesday.text == "00:00" && wednesday.text == "00:00" && thursday.text == "00:00" && friday.text == "00:00" && saturday.text == "00:00") {
                    Toast.makeText(
                        context,
                        "Please specify at least one time detail",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //Save Here
                    var classRequest = ClassRequest(
                        className,
                        classGrade,
                        classDetailLocation,
                        classLocation,
                        classMajor,
                        owner
                    )
                    var detailtime =
                        Detailtime(Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday)
                    var scheduleRequest =
                        ScheduleRequest(null, detailtime, startdate, status, totalMonth)
                    model.newClass(scheduleRequest, classRequest, pref.accessToken!!)
                    create_class.isEnabled = false
                    showMessage(LOADING)
                }
            }
        }
        model.success.observe(viewLifecycleOwner, Observer {
            alert.dismiss()
            if (it) {
                showMessage(SUCCESS)
            } else {
                showMessage(ERROR)
            }
            create_class.isEnabled = true
        })
        model.message.observe(viewLifecycleOwner, Observer {
            //            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }


    fun showMessage(type: Int) {
        var dialog = AlertDialog.Builder(context!!)
        var v = LayoutInflater.from(context).inflate(R.layout.dialog_message, null, false)
        var massage = v.findViewById<TextView>(R.id.message)
        var anim = v.findViewById<LottieAnimationView>(R.id.animation_view)
        var back = v.findViewById<TextView>(R.id.back)

        if (type == LOADING) {
            massage.text = "Please wait...."
            anim.setAnimation(R.raw.loading_profile)
            anim.loop(true)
            anim.playAnimation()
        } else if (type == ERROR) {
            massage.text = "Fail :("
            anim.setAnimation(R.raw.error)
            anim.loop(false)
            anim.playAnimation()
            back.visibility = View.VISIBLE
        } else if (type == SUCCESS) {
            massage.text = "Yeay! Class successfully created"
            anim.setAnimation(R.raw.success)
            anim.loop(false)
            anim.playAnimation()
            back.text = "Okay"
            back.visibility = View.VISIBLE
        }

        dialog.setView(v)
        alert = dialog.create()
        alert.setCancelable(false)
        alert.show()
        back.setOnClickListener {
            alert.dismiss()
            findNavController().navigateUp()
        }
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateClass().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
