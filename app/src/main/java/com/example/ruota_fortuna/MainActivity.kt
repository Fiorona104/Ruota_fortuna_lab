package com.example.ruota_fortuna // definisce il pacchetto del progetto

import android.os.Bundle // serve per creare la schermata
import android.view.animation.DecelerateInterpolator // animazione rallentata
import android.widget.Button // bottone UI
import android.widget.TextView // testo UI
import androidx.appcompat.app.AppCompatActivity // base activity Android
import kotlin.math.floor // serve per arrotondare verso il basso
import kotlin.random.Random // numeri casuali

// classe principale dell'app
class MainActivity : AppCompatActivity() {

    // collegamento ruota grafica
    private lateinit var ruota: RuotaView

    // testo del risultato vincitore
    private lateinit var risultato: TextView

    // testo storico vincitori
    private lateinit var storicoView: TextView

    // bottone gira
    private lateinit var gira: Button

    // bottone reset
    private lateinit var reset: Button

    // angolo totale della rotazione
    private var angolo = 0f

    // lista iniziale dei nomi
    private val listaIniziale = mutableListOf(
        "Luca", "Marco", "Giulia", "Sara", "Andrea", "Matteo"
    )

    // lista storico vincitori
    private val storico = mutableListOf<String>()

    // funzione che parte quando si apre l'app
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // crea activity

        setContentView(R.layout.activity_main) // collega XML

        // collega ruota dal layout
        ruota = findViewById(R.id.ruota)

        // collega testo risultato
        risultato = findViewById(R.id.risultato)

        // collega storico
        storicoView = findViewById(R.id.storico)

        // collega bottone gira
        gira = findViewById(R.id.gira)

        // collega bottone reset
        reset = findViewById(R.id.reset)

        // azione quando premi gira
        gira.setOnClickListener {

            // se non ci sono nomi esce
            if (ruota.nomi.isEmpty()) return@setOnClickListener

            // numero di giri casuale
            val giri = Random.nextInt(5, 8)

            // angolo extra casuale
            val extra = Random.nextInt(0, 360)

            // aggiorna angolo totale
            angolo += (giri * 360) + extra

            // animazione della ruota
            ruota.animate()
                .rotation(angolo)
                .setDuration(2500)
                .setInterpolator(DecelerateInterpolator())
                .withEndAction {

                    // calcola vincitore
                    val vincitore = calcolaVincitore()

                    // mostra vincitore
                    risultato.text = "Ha vinto: $vincitore"

                    // aggiunge allo storico
                    storico.add(vincitore)

                    // aggiorna testo storico
                    storicoView.text = "Storico: " + storico.joinToString(" → ")

                    // rimuove vincitore dalla ruota
                    ruota.nomi.remove(vincitore)

                    // ridisegna la ruota
                    ruota.invalidate()
                }
        }

        // azione reset
        reset.setOnClickListener {

            // reset angolo ruota
            angolo = 0f

            // ripristina lista iniziale
            ruota.nomi = listaIniziale.toMutableList()

            // reset grafico ruota
            ruota.rotation = 0f

            // reset testo risultato
            risultato.text = "Premi Gira"

            // svuota storico
            storico.clear()

            // aggiorna testo storico
            storicoView.text = "Storico:"
        }
    }

    // funzione che calcola chi ha vinto
    private fun calcolaVincitore(): String {

        // lista nomi attuali
        val nomi = ruota.nomi

        // dimensione di ogni spicchio
        val step = 360f / nomi.size

        // serve a riportare l'angolo tra 0 e 360 gradi
        val normalized = (360f - (angolo % 360f)) % 360f

        // trova indice vincitore
        val index = floor(normalized / step).toInt()

        // ritorna nome vincitore
        return nomi[index]
    }
}