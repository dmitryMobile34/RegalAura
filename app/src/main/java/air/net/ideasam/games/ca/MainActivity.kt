package air.net.ideasam.games.ca

import air.net.ideasam.games.ca.handshake.Constants
import air.net.ideasam.games.ca.handshake.Constants.DL1
import air.net.ideasam.games.ca.handshake.Constants.DL2
import air.net.ideasam.games.ca.handshake.Constants.DL3
import air.net.ideasam.games.ca.handshake.HandMeInvite
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.orhanobut.hawk.Hawk
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var stringH: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("ActivityPREF", MODE_PRIVATE)

        if (prefs.getBoolean("activity_exec", false)) {
            Intent(this, HandMeInvite::class.java).also { startActivity(it) }
            finish()
        } else {
            val exec = prefs.edit()
            exec.putBoolean("activity_exec", true)
            exec.apply()
        }

    }
    override fun onResume() {
        super.onResume()
        aps()

    }

    fun ffNow() {
        Intent(this, HandMeInvite::class.java)
            .also { startActivity(it) }
        finish()
    }

    private fun deePP() {
        AppLinkData.fetchDeferredAppLinkData(
            this
        ) { appLinkData: AppLinkData? ->
            appLinkData?.let {
                val params =
                    appLinkData.targetUri.pathSegments
                Log.d("D11PL", "$params")
                val conjoined = TextUtils.join("/", params)
                val tokenizer = StringTokenizer(conjoined, "/")
                val firstLink = tokenizer.nextToken()
                val secondLink = tokenizer.nextToken()
                val thirdLink = tokenizer.nextToken()
                Hawk.put(DL1, firstLink)
                Hawk.put(DL2, secondLink)
                Hawk.put(DL3, thirdLink)

                ffNow()
                finish()
            }
            if (appLinkData == null){
                Log.d("FB_TEST:", "Params = null")
                ffNow()
                finish()
            }
        }
    }


    private fun aps() {
        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(data: Map<String, Any>) {
                Log.d("TESTING_ZONE", "af stat is " + data["af_status"])
                stringH = (data["campaign"] as String?)!!
                Log.d("NAMING", "campaign attributed: $stringH")

                val tokenizer = StringTokenizer(stringH, "_")
                val one = tokenizer.nextToken()
                val two = tokenizer.nextToken()
                val three = tokenizer.nextToken()

                Hawk.put(Constants.C1, one)
                Hawk.put(Constants.C2, two)
                Hawk.put(Constants.C3, three)
                deePP()
                ffNow()
                finish()
            }

            override fun onConversionDataFail(error: String?) {
                Log.e("LOG_TAG", "error onAttributionFailure :  $error")

                deePP()

                ffNow()
                finish()
            }

            override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
                data?.map {
                    Log.d("LOG_TAG", "onAppOpen_attribute: ${it.key} = ${it.value}")
                }
            }

            override fun onAttributionFailure(error: String?) {
                Log.e("LOG_TAG", "error onAttributionFailure :  $error")
            }
        }
        AppsFlyerLib.getInstance().init(Constants.AF_DEV_KEY, conversionDataListener, applicationContext)
        AppsFlyerLib.getInstance().start(this)
    }
}