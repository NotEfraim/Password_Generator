package com.itechcom.passwordgenerator.presenter

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.itechcom.passwordgenerator.base.BaseActivity
import com.itechcom.passwordgenerator.base.SingleViewModel
import com.itechcom.passwordgenerator.databinding.ActivityPinBinding
import com.itechcom.passwordgenerator.extension.intentTo
import com.itechcom.passwordgenerator.extension.toastUtil
import com.itechcom.passwordgenerator.extension.transparentStatusBar
import com.itechcom.passwordgenerator.storage.StringKeys
import com.itechcom.passwordgenerator.storage.sharedPref.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder


@AndroidEntryPoint
class PinActivity : BaseActivity<ActivityPinBinding, SingleViewModel>(
    ActivityPinBinding::inflate,
    SingleViewModel::class) {

    private var sharedPrefManager : SharedPrefManager? = null
    private var pinEditTextArray : ArrayList<EditText> = arrayListOf()
    private var pinArray = arrayListOf(-1,-1,-1,-1)
    private var toConfirmPin = ""

    override fun ActivityPinBinding.initialize() {
        sharedPrefManager = viewModel.getSharedPrefManager()
        transparentStatusBar()
        initView()
        initPinEditTextBehaviour()
    }

    @SuppressLint("SetTextI18n")
    private fun initView(){
        binding.apply {
            toConfirmPin = intent.getStringExtra(StringKeys.TO_CONFIRM_PIN) ?: ""
            if(toConfirmPin.isEmpty()){
                pinState.text = if(isHavePin() == true) "INSERT PIN" else "ADD PIN"
            }else{
                pinState.text = "CONFIRM PIN"
            }

            pinEditTextArray.add(pin1)
            pinEditTextArray.add(pin2)
            pinEditTextArray.add(pin3)
            pinEditTextArray.add(pin4)

            when(pinState.text.toString()){
                "ADD PIN" -> {
                    addPinFunc()
                }
                "CONFIRM PIN" -> {
                    confirmPinFunc()
                }
                "INSERT PIN" -> {
                    insertPinFunc()
                }
            }
        }

    }

    private fun isHavePin() = sharedPrefManager?.getAppPin()?.isNotEmpty()

    private fun initPinEditTextBehaviour(){
        binding.apply {
            pinEditTextArray.mapIndexed{ index, editText ->
                editText.addTextChangedListener {
                    incorrectPinAlert.visibility = View.GONE
                    if(it?.length == 1){
                        pinArray[index] = editText.text.toString().toInt()
                        if(index < pinEditTextArray.size-1){
                            pinEditTextArray[index+1].requestFocus()
                        }
                    }else{
                        if(index > 0){
                            pinEditTextArray[index-1].requestFocus()
                            pinArray[index] = -1
                        }
                    }
                }
            }
        }
    }

    private fun getInputtedPin() : String{
        val str = StringBuilder()
        pinArray.map {
            str.append(it)
        }
        return str.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun insertPinFunc(){
        val savedPIn = sharedPrefManager?.getAppPin()
        binding.apply {
            submitBtn.setOnClickListener {
                val pin = getInputtedPin()
                if(pin.length == 4 && !pin.contains("-1")){
                    if(savedPIn.equals(pin)){
                        sharedPrefManager?.setAppPin(pin).run {
                            intentTo<MainActivity> {}
                            finish()
                        }
                    }else{
                        incorrectPinAlert.let {
                            it.text = "Incorrect Pin. Please try again."
                            it.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun addPinFunc(){
        binding.apply {
            submitBtn.setOnClickListener {
                val pin = getInputtedPin()
                if(pin.length == 4 && !pin.contains("-1")){
                    intentTo<PinActivity> {
                        it.putExtra(StringKeys.TO_CONFIRM_PIN, pin)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun confirmPinFunc(){
        binding.apply {
            submitBtn.setOnClickListener {
                val pin = getInputtedPin()
                if(pin.length == 4 && !pin.contains("-1")){
                    if(toConfirmPin == pin){
                        sharedPrefManager?.setAppPin(pin).run {
                            intentTo<MainActivity> {}
                            finish()
                        }
                    }else{
                        incorrectPinAlert.let {
                            it.text = "Pin not match."
                            it.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
}