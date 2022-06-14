package com.sibaamap.textinputedittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import com.sibaamap.textinputedittext.databinding.ActivityMainBinding
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailFocusListener()
        passFocusListener()
        phoneFocusListener()

        binding.btnSubmit.setOnClickListener {
            submitForm()
        }

    }

    private fun submitForm() {
        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validPhone = binding.phoneContainer.helperText == null

        if(validEmail && validPassword && validPhone){
            resetForm()
        }else{
            invalidForm()
        }
    }

    private fun invalidForm() {
        var message = ""
        if(binding.emailContainer.helperText == null)
            message += "\n\nEmail: "+binding.emailContainer.helperText
        if(binding.passwordContainer.helperText == null)
            message += "\n\nPassword: "+binding.passwordContainer.helperText
        if(binding.phoneContainer.helperText == null)
            message += "\n\nPhone: "+binding.phoneContainer.helperText
        AlertDialog.Builder(this)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Ok"){ _,_ ->
                //
            }.show()

    }

    private fun resetForm() {
            var message = "Email: "+binding.emailEditText.text
            message += "\nPassword: "+binding.passwordEditText.text
            message += "\nPhone: "+binding.phoneEditText.text
        AlertDialog.Builder(this)
            .setTitle("Form submitted")
            .setMessage(message)
            .setPositiveButton("Ok"){ _,_ ->
                binding.emailEditText.text = null
                binding.passwordEditText.text = null
                binding.phoneEditText.text = null

                binding.emailContainer.helperText = getString(R.string.required)
                binding.passwordContainer.helperText = getString(R.string.required)
                binding.phoneContainer.helperText = getString(R.string.required)
            }
            .show()
    }

    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener{ _, focused ->
            if(!focused){
                binding.emailContainer.helperText = validEmail()
            }

        }
    }

    private fun validEmail(): String?
    {
        val emailTex = binding.emailEditText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailTex).matches()){
            return "Invalid Email Address"
        }
        return null
    }
    private fun passFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener{ _, focused ->
            if(!focused){
                binding.passwordContainer.helperText = validPassword()
            }

        }
    }

    private fun validPassword(): String?
    {
        val passwordText = binding.passwordEditText.text.toString()
        if(passwordText.length < 8){
            return "Minimum 8 Character Password"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex())){
            return "Must Contain 1 Upper-case Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex())){
            return "Must Contain 1 Lower-case Character"
        }
        if(!passwordText.matches(".*[@#\$^&+=].*".toRegex())){
            return "Must Contain 1 Special Character (@#\$^&+=)"
        }
        return null
    }

    private fun phoneFocusListener() {
        binding.phoneEditText.setOnFocusChangeListener{ _, focused ->
            if(!focused){
                binding.phoneContainer.helperText = validPhone()
            }

        }
    }

    private fun validPhone(): String?
    {
        val phoneText = binding.phoneEditText.text.toString()
        if(!phoneText.matches(".*[0-9].*".toRegex())){
            return "Must be 10 Digits"
        }
        if(phoneText.length != 10){
            return "Must be 10 Digits"
        }
        return null
    }



}