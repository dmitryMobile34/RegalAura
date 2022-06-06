package air.net.ideasam.games.ca.handshake

import air.net.ideasam.games.ca.R
import air.net.ideasam.games.ca.WhiteProgress
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.*

class HandMeInvite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hand_me_invite)
        val txtEr: TextView = findViewById(R.id.txtMainMain)

        runBlocking {

            val job: Job = GlobalScope.launch(Dispatchers.IO) {
                getAsync(applicationContext)
            }
            job.join()
            val jsoup: String? = Hawk.get(Constants.asyncResult, "")
            Log.d("cora", "cora $jsoup")

            txtEr.text = jsoup

            if (jsoup == "j9bh") {
                Intent(applicationContext, WhiteProgress::class.java).also { startActivity(it) }
            } else {
                Intent(applicationContext, BBRAct::class.java).also { startActivity(it) }
            }
            finish()
        }
    }

    private suspend fun getAsync(context: Context) {
        val asyncKey = AsyncJsoup(context)
        val asyncResult = asyncKey.getDocSecretKey()
        Hawk.put(Constants.asyncResult, asyncResult)
    }
}
