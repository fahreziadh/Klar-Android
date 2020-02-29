package id.fahrezi.klar.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.ale.listener.StartResponseListener
import com.ale.rainbowsdk.RainbowSdk
import id.fahrezi.klar.R
import id.fahrezi.klar.service.model.Request.LoginRequest
import id.fahrezi.klar.service.model.Request.RegisterRequest
import id.fahrezi.klar.service.model.Response.AuthResponse
import id.fahrezi.klar.service.repository.PreferenceHelper
import id.fahrezi.klar.util.Validator
import id.fahrezi.klar.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_auth.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Auth : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var isLogin = false
    private lateinit var model: AuthViewModel
    lateinit var alert: AlertDialog

    var LOADING = 1
    var ERROR = 2
    var SUCCESS = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        model = ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alert = AlertDialog.Builder(context!!).create()
        checkAuth()
        setPrimaryButton()
        setSecondaryButton()

        //Handle Auth Response
        model.auth.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                successAuth(it)
            }
        })

        //Handle Auth Error
        model.error.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                alert.dismiss()
                Toast.makeText(context!!, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun showMessage(type: Int) {
        var dialog = AlertDialog.Builder(context!!)
        var v = LayoutInflater.from(context).inflate(R.layout.dialog_message, null, false)
        var massage = v.findViewById<TextView>(R.id.message)
        var anim = v.findViewById<LottieAnimationView>(R.id.animation_view)

        if (type == LOADING) {
            massage.text = "Please wait...."
            anim.setAnimation(R.raw.loading_profile)
            anim.loop(true)
            anim.playAnimation()
        }

        dialog.setView(v)
        alert = dialog.create()
        alert.show()
    }

    fun checkAuth() {
        var accessToken = PreferenceHelper(context!!).accessToken
        if (accessToken != "") {
            findNavController().popBackStack(R.id.home, true)
        }
    }

    fun setPrimaryButton() {
        button1.setOnClickListener {
            var username = username.text.toString()
            var email = email.text.toString()
            var password = password.text.toString()
            if (isLogin) { //do login
                var request = LoginRequest(username, password)
                model.login(request)
                showMessage(LOADING)
            } else {
                if (validateForm()) { // do register
                    var request = RegisterRequest(email, "", "", password, username)
                    model.register(request)
                    showMessage(LOADING)
                }
            }
        }
    }


    fun successAuth(it: AuthResponse) {
        alert.dismiss()
        var pref = PreferenceHelper(context!!)
        pref.accessToken = it.accesstoken
        pref.userEmail = it.user.email
        pref.userFullname = it.user.fullname
        pref.userName=it.user.username
        pref.userImageProfile = it.user.imageprofile
        pref.userId = it.user.id
        findNavController().popBackStack(R.id.home, false)
    }

    fun validateForm(): Boolean {
        var valid: Boolean
        if (Validator.isValidEmail(email) && Validator.isValidPassword(password) && Validator.isValidName(
                username
            )
        ) {
            valid = true
        } else {
            valid = false
            Toast.makeText(context, "Enter a valid Data", Toast.LENGTH_SHORT).show()
        }

        return valid
    }

    fun setSecondaryButton() {
        button2.setOnClickListener {
            if (isLogin) {
                email.visibility = View.VISIBLE
                button1.text = "Create Account"
                button2.text = "Login"
                message.text = "Create New Account"
            } else {
                button1.text = "Login"
                button2.text = "Create Account"
                message.text = "Wellcome"
                email.visibility = View.GONE
            }
            isLogin = !isLogin
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
        alert.dismiss()
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Auth().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
