

package com.example.ruota_fortuna


import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {


    private lateinit var ruota: ImageView
    private lateinit var gira: Button
    private lateinit var reset: Button
    private lateinit var risultato: TextView
    private lateinit var stato: TextView


    private var angolo = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // collegamento elementi grafici
        ruota = findViewById(R.id.ruota)
        gira = findViewById(R.id.gira)
        reset = findViewById(R.id.reset)
        risultato = findViewById(R.id.risultato)
        stato = findViewById(R.id.stato)


        // gira ruota quando premo bottone gira
        gira.setOnClickListener {


            stato.text = "START"
            // aumenta l’angolo in modo casuale per simulare la rotazione
            angolo += 360f + Random.nextInt(0, 360)


            // animazione della ruota
            ruota.animate()
                .rotation(angolo)
                .setDuration(1000)
                .withEndAction {
                    // lista dei possibili risultati (bisogna riuscire a poterli far mettere all'utente)
                    val opzioni = listOf("1", "2", "3", "4")
                    val estratto = opzioni.random() //risultato


                    //stampa a schermo
                    risultato.text = estratto
                    stato.text = "Pronto"
                }
        }


        // reset della ruota quando schiaccio tasto
        reset.setOnClickListener {
            // reset dell’angolo della ruota
            angolo = 0f
            ruota.rotation = 0f
            // reset testi a schermo
            risultato.text = "Premi Gira"
            stato.text = "Reset"
        }
    }
}
