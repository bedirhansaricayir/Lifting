package com.lifting.app.domain.model

import com.lifting.app.core.constants.Constants.Companion.BES_GUN
import com.lifting.app.core.constants.Constants.Companion.BES_GUN_FORM_KORU
import com.lifting.app.core.constants.Constants.Companion.BES_GUN_KAS_KAZAN
import com.lifting.app.core.constants.Constants.Companion.BES_GUN_YAG_YAK
import com.lifting.app.core.constants.Constants.Companion.HIC
import com.lifting.app.core.constants.Constants.Companion.HIC_FORM_KORU
import com.lifting.app.core.constants.Constants.Companion.HIC_KAS_KAZAN
import com.lifting.app.core.constants.Constants.Companion.HIC_YAG_YAK
import com.lifting.app.core.constants.Constants.Companion.IKI_UC_GUN
import com.lifting.app.core.constants.Constants.Companion.IKI_UC_GUN_FORM_KORU
import com.lifting.app.core.constants.Constants.Companion.IKI_UC_GUN_KAS_KAZAN
import com.lifting.app.core.constants.Constants.Companion.IKI_UC_GUN_YAG_YAK

data class ProgramState(val burnFat: String, val gainMuscle: String, val maintenance: String)

val programMap = mapOf(
    HIC to ProgramState(
        HIC_YAG_YAK,
        HIC_KAS_KAZAN,
        HIC_FORM_KORU
    ),
    IKI_UC_GUN to ProgramState(
        IKI_UC_GUN_YAG_YAK,
        IKI_UC_GUN_KAS_KAZAN,
        IKI_UC_GUN_FORM_KORU
    ),
    BES_GUN to ProgramState(
        BES_GUN_YAG_YAK,
        BES_GUN_KAS_KAZAN,
        BES_GUN_FORM_KORU
    )
)