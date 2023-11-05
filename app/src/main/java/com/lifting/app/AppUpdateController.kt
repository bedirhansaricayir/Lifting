package com.lifting.app

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.lifting.app.common.constants.AppUpdateConstants
import com.lifting.app.common.constants.AppUpdateConstants.FLEXIBLE
import com.lifting.app.common.constants.AppUpdateConstants.IMMEDIATE
import com.lifting.app.common.constants.AppUpdateConstants.UPDATE_TYPE
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class AppUpdateController @Inject constructor(
    @ActivityContext private val context: Context,
) {
    private val appUpdateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(context) }
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private var updateType: String? = FLEXIBLE
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val installStateUpdatedListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            showToast(R.string.update_successful_label)
            coroutineScope.launch {
                delay(5.seconds)
                appUpdateManager.completeUpdate()
            }
        }
    }

    private fun showToast(messageResId: Int) {
        Toast.makeText(context, context.getText(messageResId), Toast.LENGTH_LONG).show()
    }

    fun initRemoteConfig() {
        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 120
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateType = remoteConfig.getString(UPDATE_TYPE)
                Log.d("MainActivity", "Config params update successful: $updateType")
                handleUpdate()
            } else {
                Log.d("MainActivity", "Config params update failed")

            }
        }
    }

    private fun handleUpdate() {
        when (updateType) {
            FLEXIBLE -> checkForFlexibleUpdate()
            IMMEDIATE -> checkForImmediateUpdate()
        }
    }

    private fun checkForFlexibleUpdate() {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(
                    AppUpdateType.FLEXIBLE
                )
            ) {
                appUpdateManager.registerListener(installStateUpdatedListener)
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo, AppUpdateType.FLEXIBLE, context as Activity,
                    AppUpdateConstants.FLEXIBLE_UPDATE_REQUEST_CODE
                )
            }
        }
    }

    private fun checkForImmediateUpdate() {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(
                    AppUpdateType.IMMEDIATE
                )
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo, AppUpdateType.IMMEDIATE, context as Activity,
                    AppUpdateConstants.IMMEDIATE_UPDATE_REQUEST_CODE
                )
            }
        }
    }

    fun resumeUpdate() {
        if (updateType == IMMEDIATE) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        info, AppUpdateType.IMMEDIATE, context as Activity,
                        AppUpdateConstants.IMMEDIATE_UPDATE_REQUEST_CODE
                    )
                }
            }
        }
    }

    fun unregisterListener() {
        if (updateType == FLEXIBLE) {
            appUpdateManager.unregisterListener(installStateUpdatedListener)
        }
    }
}