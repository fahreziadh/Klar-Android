package id.fahrezi.klar.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import id.fahrezi.klar.R
import id.fahrezi.klar.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_auth.*
import android.text.TextUtils
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import id.fahrezi.klar.util.Validator
import id.fahrezi.klar.service.model.Request.RegisterRequest
import id.fahrezi.klar.service.model.Request.LoginRequest


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Auth : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var isLogin = false
    private lateinit var model: AuthViewModel
    lateinit var dialog: AlertDialog.Builder

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
        setPrimaryButton()
        setSecondaryButton()

        //Handle Auth Response
        model.auth.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                lateinit var dialog: AlertDialog.Builder
                Toast.makeText(context!!, it.accesstoken, Toast.LENGTH_SHORT).show()
            }
        })

        //Handle Auth Error
        model.error.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(context!!, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun showMessage(type: Int) {
        dialog = AlertDialog.Builder(context!!)
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
        var alert = dialog.create()
        alert.show()
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
