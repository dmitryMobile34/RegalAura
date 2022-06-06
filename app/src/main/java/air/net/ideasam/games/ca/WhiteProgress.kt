package air.net.ideasam.games.ca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_white_progress.*
import kotlin.random.Random

class WhiteProgress : AppCompatActivity() {

    private var rabbitCount = 0
    private var foxCount = 0
    private var bearCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_white_progress)

        Glide
            .with(applicationContext)
            .load("https://cdn.dribbble.com/users/2101543/screenshots/16170352/fire_dribble.gif")
            .apply(
                RequestOptions().centerCrop()
            )
            .into(backImage)

        vibrate()

        rabbit.setOnClickListener {
            rabbitCount++
            firstIcon.visibility = View.VISIBLE
            clickResponse(rabbit, firstCount)
        }

        fox.setOnClickListener {
            foxCount++
            secondIcon.visibility = View.VISIBLE
            clickResponse(fox, secondCount)
        }

        bear.setOnClickListener {
            bearCount++
            thirdIcon.visibility = View.VISIBLE
            clickResponse(bear, thirdCount)
        }

    }

    private fun randomPosition() = Random.nextInt(-400, 400).toFloat()

    private fun vibrate() {
        rabbit.animate()
            .translationX(randomPosition())
            .translationY(randomPosition())
            .setDuration(1000)
            .withEndAction(::vibrate)
            .start()

        fox.animate()
            .translationX(randomPosition())
            .translationY(randomPosition())
            .setDuration(1000)
            .withEndAction(::vibrate)
            .start()

        bear.animate()
            .translationX(randomPosition())
            .translationY(randomPosition())
            .setDuration(1000)
            .withEndAction(::vibrate)
            .start()
    }

    private fun clickResponse(imageView: ImageView, textView: TextView) {
        imageView.animate().cancel()
        imageView.visibility = View.INVISIBLE
        textView.text = rabbitCount.toString()
        Handler().postDelayed({
            vibrate()
            imageView.visibility = View.VISIBLE
        }, 1000)

    }


}