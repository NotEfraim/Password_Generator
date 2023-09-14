package com.itechcom.passwordgenerator.presenter

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.itechcom.passwordgenerator.base.BaseActivity
import com.itechcom.passwordgenerator.base.SingleViewModel
import com.itechcom.passwordgenerator.databinding.ActivitySplashScreenBinding
import com.itechcom.passwordgenerator.extension.intentTo
import com.itechcom.passwordgenerator.extension.transparentStatusBar
import com.itechcom.passwordgenerator.storage.StringKeys
import com.itechcom.passwordgenerator.storage.sharedPref.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreen : BaseActivity<ActivitySplashScreenBinding, SingleViewModel>(
    ActivitySplashScreenBinding::inflate,
    SingleViewModel::class) {

    private var isFreshInstall = true
    private var sharedPrefManager : SharedPrefManager? = null

    override fun ActivitySplashScreenBinding.initialize() {
        transparentStatusBar()
        splashValidator()
    }

    private fun splashValidator(){
        sharedPrefManager = viewModel.getSharedPrefManager()
        sharedPrefManager?.getBoolean(
            StringKeys.isFreshInstall,
            true)?.let {
            isFreshInstall = it
            sharedPrefManager?.setBoolean(StringKeys.isFreshInstall, false)
        }
        if(isFreshInstall)  initView() else {
            intentTo<PinActivity> {
                it.putExtra(StringKeys.PinActivity_STATUS, "INSERT PIN")
            }
            finish()
        }
    }

    private fun initView(){
        binding.apply {
            startBtn.setOnClickListener {
                startBtn.animate().alpha(0F).withEndAction {
                    startBtn.isEnabled = false
                    loading.visibility = View.VISIBLE
                    lifecycleScope.launch {  delayedIntent() }
                }.duration = 300
            }
        }
    }


    private suspend fun delayedIntent(){
        lifecycleScope.launch {
            delay(2000)
            intentTo<PinActivity>{}
            finish()
        }
    }
}