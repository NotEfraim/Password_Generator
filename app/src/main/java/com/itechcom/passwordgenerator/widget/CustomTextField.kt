package com.itechcom.passwordgenerator.widget

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.itechcom.passwordgenerator.R
import com.itechcom.passwordgenerator.databinding.WidgetTextFieldBinding
import com.itechcom.passwordgenerator.extension.toastUtil

class CustomTextField @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    desStyle : Int = 0
) : ConstraintLayout(context, attributeSet, desStyle) {

    val binding = WidgetTextFieldBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.CustomTextField)
        val startIcon = a.getInt(R.styleable.CustomTextField_startIcon, R.mipmap.instagram)
        val endIcon = a.getInt(R.styleable.CustomTextField_endIcon, R.mipmap.copy_icon)
        val showStartIcon = a.getBoolean(R.styleable.CustomTextField_showStartIcon, true)
        val showEndIcon = a.getBoolean(R.styleable.CustomTextField_showEndIcon, true)
        val fieldText = a.getString(R.styleable.CustomTextField_fieldText)
        setStartIcon(startIcon)
        setEndIcon(endIcon)
        showStartIcon(showStartIcon)
        showEndIcon(showEndIcon)
        setFieldText(fieldText)
        a.recycle()
    }

    fun setStartIcon(int : Int){
        binding.startIcon.setImageResource(int)
    }

    fun setEndIcon(int : Int){
        binding.endIcon.setImageResource(int)
    }

    fun showStartIcon (show : Boolean){
        binding.startIcon.visibility = if(show) VISIBLE else GONE
    }

    fun showEndIcon (show : Boolean){
        binding.endIcon.visibility = if(show) VISIBLE else GONE
    }

    fun setFieldText(text : String?){
        binding.widgetText.text = text
    }

    fun initOnClick(dbPassword : String? = null){
        binding.apply {
            endIcon.setOnClickListener {
                val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                if(dbPassword.isNullOrEmpty()){
                    widgetText.text.toString().let { generatedPass ->
                        val clipData = ClipData.newPlainText("passwordText", generatedPass)
                        clipboardManager.setPrimaryClip(clipData)
                        if(generatedPass.isNotEmpty()) context.toastUtil("Password Copied!")
                    }
                }else{
                    dbPassword.let { dbPass ->
                        val clipData = ClipData.newPlainText("passwordText", dbPass)
                        clipboardManager.setPrimaryClip(clipData)
                        if(dbPass.isNotEmpty()) context.toastUtil("Password Copied!")
                    }
                }

            }
        }
    }
}