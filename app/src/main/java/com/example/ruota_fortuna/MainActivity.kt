package com.example.ruota_fortuna

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var ruota: ImageView
    private lateinit var gira: Button
    private lateinit var reset: Button

    private var angolo = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ruota = findViewById(R.id.ruota)
        gira = findViewById(R.id.gira)
        reset = findViewById(R.id.reset)

        gira.setOnClickListener {
            angolo += 360f
            ruota.animate()
                .rotation(angolo)
                .setDuration(1000)
        }

        reset.setOnClickListener {
            angolo = 0f
            ruota.animate()
                .rotation(0f)
                .setDuration(500)
            //code
        }
    }
}
