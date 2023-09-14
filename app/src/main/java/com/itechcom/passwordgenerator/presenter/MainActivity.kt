package com.itechcom.passwordgenerator.presenter

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.itechcom.passwordgenerator.adapter.SavePasswordAdapter
import com.itechcom.passwordgenerator.base.BaseActivity
import com.itechcom.passwordgenerator.base.SingleViewModel
import com.itechcom.passwordgenerator.databinding.ActivityMainBinding
import com.itechcom.passwordgenerator.extension.collect
import com.itechcom.passwordgenerator.extension.intentTo
import com.itechcom.passwordgenerator.extension.mapper
import com.itechcom.passwordgenerator.extension.toastUtil
import com.itechcom.passwordgenerator.extension.transparentStatusBar
import com.itechcom.passwordgenerator.extension.useEmptyView
import com.itechcom.passwordgenerator.storage.StringKeys
import com.itechcom.passwordgenerator.storage.room.UserEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, SingleViewModel>(
    ActivityMainBinding::inflate,
    SingleViewModel::class) {

    private val savePasswordAdapter = SavePasswordAdapter()

    override fun onResume() {
        super.onResume()
        viewModel.getAllData()
    }

    override fun SingleViewModel.initObserver() {
        collect(allData, ::initData )
    }

    private fun initData(data : List<UserEntity?>){
        savePasswordAdapter.submitList(data.mapper())
    }


    override fun ActivityMainBinding.initialize() {
        transparentStatusBar()
        initView()
    }

    private fun initView(){
        binding.apply {
            GeneratedPassword.initOnClick()
            accountsRecycler.adapter = savePasswordAdapter
            savePasswordAdapter.useEmptyView()
            moreBtn.setOnClickListener {
                intentTo<AccountListActivity>{}
            }
            generateBtn.setOnClickListener {
                generatePassword()
            }
            addAccountBtn.setOnClickListener {
                intentTo<AddOrViewAccount> {  }
            }
            menu.setOnClickListener {
                toastUtil("Coming on version 1.1")
            }
        }
        initAdapterClick()
        initSeekBar()
    }

    private fun initSeekBar(){
        binding.apply {
            lengthSeekBar.max = (25 * 1000)
            lengthSeekBar.progress = (10 * 1000)
            lengthSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    progressCounter.text = (progress/1000).toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
    }

    private fun generatePassword(){
        val default =  ('a'..'z')
        val numberChars = ('0'..'9')
        val uppercaseChars = ('A'..'Z')
        val symbolsChars = "!@#\$%^&*()_+-=<>?/".toList()
        val allowedChars : ArrayList<Iterable<Char>> = arrayListOf(default)

        if(isUppercaseEnable()) allowedChars.add(uppercaseChars)
        if(isNumberEnable()) allowedChars.add(numberChars)
        if(isSymbolEnable()) allowedChars.add(symbolsChars)

        val length = binding.progressCounter.text.toString().toInt()
        val randomizedPassword = (1..length)
            .map {
                val charSet = allowedChars.random().toList()
                charSet.random()
            }
            .joinToString("")
            .replace("..", "")
            .replace(" ","")
            .substring(0, length)

        binding.GeneratedPassword.setFieldText(randomizedPassword)

    }

    private fun isUppercaseEnable() = binding.upperCaseCheckBox.isChecked

    private fun isNumberEnable() = binding.numbersCheckBox.isChecked

    private fun isSymbolEnable() = binding.symbolsCheckBox.isChecked

    private fun initAdapterClick(){
        savePasswordAdapter.setOnItemClickListener { adapter, _, position ->
            val data = adapter.items[position]
            intentTo<AddOrViewAccount>{
                it.putExtra(StringKeys.SAVED_ID_KEY, data.id)
                it.putExtra(StringKeys.SAVED_ACCOUNT_TYPE_KEY, data.accountType)
                it.putExtra(StringKeys.SAVED_DESC_KEY, data.accountDescription)
                it.putExtra(StringKeys.SAVED_PASSWORD_KEY, data.accountPassword)
            }
        }
    }


}