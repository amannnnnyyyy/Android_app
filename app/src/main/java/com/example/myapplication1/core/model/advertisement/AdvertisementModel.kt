package com.example.myapplication1.core.model.advertisement

import android.net.Uri
import androidx.core.net.toUri
import com.example.myapplication1.R

data class AdvertisementModel(val adPic: Int, val redirectTo: Uri)

object Ads{
    val ads = listOf<AdvertisementModel>(
        AdvertisementModel(R.drawable.ethiotel, "https://www.ethiotelecom.et".toUri()),
        AdvertisementModel(R.drawable.temu, "https://www.temu.com".toUri()),
        AdvertisementModel(R.drawable.coca, "https://www.coca-colacompany.com".toUri()),
        AdvertisementModel(R.drawable.colagate , "https://www.colgate.com".toUri())
    )
}
