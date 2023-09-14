package com.itechcom.passwordgenerator.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.chad.library.adapter.base.BaseQuickAdapter
import com.itechcom.passwordgenerator.R
import com.itechcom.passwordgenerator.model.SavePasswordItem
import com.itechcom.passwordgenerator.storage.room.UserEntity
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


fun Activity.transparentStatusBar() {
    window.clearFlags(
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
    )
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = Color.TRANSPARENT
}

fun ArrayList<SavePasswordItem>.mockAdapterData(){
    for (i in 0 until 3){
        val item = SavePasswordItem(accountDescription = "Kirito Cute", accountType = "Facebook", accountPassword = "fyfguhijs09yt74whi")
        add(item)
    }
}

inline fun <reified T : Any>Context.intentTo(unit :(intent : Intent) -> Unit){
    val intent = Intent(this, T::class.java)
    unit.invoke(intent).run {
        startActivity(intent)
    }
}

fun ImageView.backFinish(){
    val context = this.context
    this.setOnClickListener { (context as Activity).finish() }
}

fun Context.toastUtil(msg : String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun List<UserEntity?>.mapper() : ArrayList<SavePasswordItem> {
    val data : ArrayList<SavePasswordItem> = arrayListOf()
    this.map{
        data.add(
            SavePasswordItem(
                id = it?.id ,
                accountType = it?.accountType,
                accountDescription = it?.description,
                accountPassword = it?.password
            )
        )
    }
    return data
}

fun BaseQuickAdapter<*, *>.useEmptyView(){
    isEmptyViewEnable = true
    setEmptyViewLayout(context, R.layout.layout_empty_view)
}

fun <T: Any>LifecycleOwner.collect(data: SharedFlow<T?>, function: (T) -> Unit) {
    this.lifecycleScope.launch {
        data.collectLatest{
            if (it != null) {
                function(it)
            }
        }
    }
}