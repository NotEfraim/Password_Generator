package com.itechcom.passwordgenerator.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

abstract class BaseActivity<VB: ViewBinding, VM: ViewModel>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB,
    model: KClass<out VM>
) : AppCompatActivity(){

    val binding by lazy { bindingInflater(layoutInflater) }
    val viewModel by ViewModelLazy(
        model,
        { this.viewModelStore },
        {this.defaultViewModelProviderFactory },
        {this.defaultViewModelCreationExtras }
    )

    open fun VB.initialize(){}
    open fun VM.initCall(){}
    open fun VM.initObserver(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.initialize()
        viewModel.initObserver()
        viewModel.initCall()
        setContentView(binding.root)
    }

    @SuppressLint("ResourceType")
    fun addFragment(id:Int, container: ViewGroup?){
        val frameLayout = FrameLayout(this)
        frameLayout.id = id
        val layoutParams =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        frameLayout.layoutParams = layoutParams
        if(container==null){
            (binding.root as ViewGroup).addView(frameLayout)
        }else{
            container.addView(frameLayout)
        }
        val fragment = getFragment(id)
        supportFragmentManager.beginTransaction().add(id,fragment,fragment::class.qualifiedName+id).commit()
    }

    open fun getFragment(id:Int): Fragment = TODO()

}