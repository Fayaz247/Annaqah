package com.example.annaqah.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConfirmedData(
    val confirmedImage: String,
    val confirmedItemName: String,
    val confirmedPrice: String,
    val confirmedSize: String
) : Parcelable
