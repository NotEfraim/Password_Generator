package com.itechcom.passwordgenerator.widget

import android.content.ClipboardManager
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.itechcom.passwordgenerator.R
import com.itechcom.passwordgenerator.databinding.WidgetEditTextFieldBinding
import com.itechcom.passwordgenerator.databinding.WidgetTextFieldBinding
import com.itechcom.passwordgenerator.extension.toastUtil

class CustomEditTextField @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    desStyle : Int = 0
) : ConstraintLayout(context, attributeSet, desStyle) {

    val binding = WidgetEditTextFieldBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.CustomEditTextField)
        val startIcon = a.getResourceId(R.styleable.CustomEditTextField_startIconEt, R.mipmap.instagram)
        val endIcon = a.getResourceId(R.styleable.CustomEditTextField_endIconEt, R.mipmap.paste_icon)
        val showStartIcon = a.getBoolean(R.styleable.CustomEditTextField_showStartIconEt, true)
        val showEndIcon = a.getBoolean(R.styleable.CustomEditTextField_showEndIconEt, true)
        val fieldText = a.getString(R.styleable.CustomEditTextField_fieldTextEt)
        val hintText = a.getString(R.styleable.CustomEditTextField_hintText)
        setStartIcon(startIcon)
        setEndIcon(endIcon)
        showStartIcon(showStartIcon)
        showEndIcon(showEndIcon)
        setFieldText(fieldText)
        setHintText(hintText)
        a.recycle()
        initPasteFunc()
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
        binding.widgetEditText.setText(text)
    }

    fun setHintText(text : String?){
        binding.widgetEditText.hint = text
    }

    fun getFieldText() = binding.widgetEditText.text.toString()

    private fun initPasteFunc(){
        binding.apply {
            endIcon.setOnClickListener {
                val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                if(clipboardManager.hasPrimaryClip()){
                    val clipData = clipboardManager.primaryClip
                    if(clipData != null && clipData.itemCount > 0){
                        val textToPaste = "${clipData.getItemAt(0).text}"
                        if(textToPaste.isNotEmpty()){
                            widgetEditText.setText(textToPaste)
//                            context.toastUtil("Text pasted!")
                        }
                    }
                }
            }
        }

    }
}