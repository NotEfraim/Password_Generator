package com.itechcom.passwordgenerator.model

import com.google.gson.annotations.SerializedName

data class SavePasswordModel(
    @SerializedName("data")
    var data : SavePasswordItem? = null,
)

data class SavePasswordItem(
    @SerializedName("id")
    var id : Int? = 0,
    @SerializedName("accountType")
    var accountType : String? = null,
    @SerializedName("accountDescription")
    var accountDescription : String? = null,
    @SerializedName("accountPassword")
    var accountPassword : String? = null
)