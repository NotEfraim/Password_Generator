package com.itechcom.passwordgenerator.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.itechcom.passwordgenerator.R
import com.itechcom.passwordgenerator.databinding.WidgetDropDownBinding
import com.itechcom.passwordgenerator.databinding.WidgetTextFieldBinding

class CustomSpinner @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    desStyle : Int = 0
) : ConstraintLayout(context, attributeSet, desStyle) {

    val binding = WidgetDropDownBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.CustomSpinner)
        val startIcon = a.getResourceId(R.styleable.CustomSpinner_spinnerStartIcon, R.mipmap.description_icon)
        setSpinnerIcon(startIcon)
        a.recycle()
    }

    fun setSpinnerIcon(res : Int){
        binding.startIcon.setImageResource(res)
    }

    fun setSpinnerAdapter(arrayAdapter: ArrayAdapter<String>){
        binding.widgetSpinner.adapter = arrayAdapter
    }

    fun getSpinner() = binding.widgetSpinner

}