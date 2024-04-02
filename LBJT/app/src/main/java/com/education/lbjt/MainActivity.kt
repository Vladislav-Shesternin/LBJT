package com.education.lbjt

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.education.lbjt.databinding.ActivityMainBinding
import com.education.lbjt.game.data.User
import com.education.lbjt.util.DataStoreManager
import com.education.lbjt.util.Lottie
import com.education.lbjt.util.Once
import com.education.lbjt.util.log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), AndroidFragmentApplication.Callbacks {

    private val coroutine = CoroutineScope(Dispatchers.Default)
    private val onceExit  = Once()

    private lateinit var binding : ActivityMainBinding
    lateinit var lottie          : Lottie
    //lateinit var banner          : AdView
    //lateinit var rewardedUtil    : RewardedUtil

    val user = User()

    var isUpdateAvailable = false

    // registerForActivityResult
    private var pickMediaBlock: (Uri?) -> Unit = { }
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri -> pickMediaBlock(uri) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        getFirebaseRemoteConfig()

        asyncCheckInternetConnection()
        asyncGenerateUserId()

        lottie.showLoader()
    }

    override fun exit() {
        onceExit.once {
            log("exit")
            coroutine.launch(Dispatchers.Main) {
                finishAndRemoveTask()
                delay(100)
                exitProcess(0)
            }
        }
    }

    private fun initialize() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lottie       = Lottie(binding)
        //banner       = binding.banner.apply { loadAd(AdRequest.Builder().build()) }
        //rewardedUtil = RewardedUtil(this, coroutine)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun asyncCheckInternetConnection() {
//        coroutine.launch(Dispatchers.Main) {
//            while (isActive) {
//                delay(10_000)
//                banner.setVisible(if (internetConnection()) View.VISIBLE else View.GONE)
//            }
//        }
    }

    private fun asyncGenerateUserId() {
        coroutine.launch(Dispatchers.IO) {
            DataStoreManager.UserId.update {
                user.id = it ?: UUID.randomUUID().toString()
                user.id!!
            }
            DataStoreManager.UserNickname.collect { user.nickname = it }
        }
    }

    fun setNavigationBarColor(@ColorRes colorId: Int) {
        coroutine.launch(Dispatchers.Main) {
            window.navigationBarColor = ContextCompat.getColor(this@MainActivity, colorId)
        }
    }

    fun launchPickMedia(type: ActivityResultContracts.PickVisualMedia.VisualMediaType, block: (Uri?) -> Unit) {
        pickMediaBlock = block
        pickMedia.launch(PickVisualMediaRequest(type))
    }

    private fun getFirebaseRemoteConfig() {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 3600 })
            fetchAndActivate().addOnCompleteListener(this@MainActivity) { task ->
                try {
                    if (task.isSuccessful) {
                        val version = getLong("version")
                        if (version > BuildConfig.VERSION_CODE) isUpdateAvailable = true
                        log("getFirebaseRemoteConfig success: $version")
                    } else {
                        log("getFirebaseRemoteConfig failure: ${this.all}")
                    }
                } catch (e: Exception) {
                    log("getFirebaseRemoteConfig exception: = ${e.message}")
                }
            }
        }
    }

}