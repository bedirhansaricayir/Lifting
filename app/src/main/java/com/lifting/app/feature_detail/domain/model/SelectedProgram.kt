package com.lifting.app.feature_detail.domain.model

import android.os.Parcelable
import com.lifting.app.feature_home.data.remote.model.Uygulanis
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedProgram(
    val programName: String,
    val programDay: Int,
    val program: ArrayList<Uygulanis>
):Parcelable