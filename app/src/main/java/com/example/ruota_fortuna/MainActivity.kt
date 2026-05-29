package com.example.ruota_fortuna // nome del pacchetto del progetto

import android.animation.ObjectAnimator // serve per animazioni (coriandoli che cadono)
import android.graphics.Color // gestione colori casuali
import android.media.MediaPlayer // gestione audio (suoni)
import android.os.Bundle // ciclo di vita dell'Activity
import android.view.animation.DecelerateInterpolator // rallenta la rotazione alla fine
import android.widget.Button // pulsanti UI
import android.widget.FrameLayout // contenitore principale (per coriandoli)
import android.widget.TextView // testi a schermo
import androidx.appcompat.app.AppCompatActivity // classe base Activity Android
import kotlin.math.floor // serve per calcolare indice vincitore
import kotlin.random.Random // genera numeri casuali

// Activity principale dell'app
class MainActivity : AppCompatActivity() {

    // riferimento alla ruota grafica
    private lateinit var ruota: RuotaView

    // testo che mostra il vincitore
    private lateinit var risultato: TextView

    // testo che mostra lo storico vincitori
    private lateinit var storicoView: TextView

    // bottone per far girare la ruota
    private lateinit var gira: Button

    // bottone per resettare il gioco
    private lateinit var reset: Button

    // suono iniziale (sigla)
    private lateinit var siglaSound: MediaPlayer

    // suono durante rotazione
    private lateinit var giraSound: MediaPlayer

    // suono quando esce il risultato
    private lateinit var risultatoSound: MediaPlayer

    // angolo totale della rotazione
    private var angolo = 0f

    // lista iniziale dei partecipanti
    private val listaIniziale = mutableListOf(
        "Luca", "Marco", "Giulia", "Sara", "Andrea", "Matteo"
    )

    // lista che salva lo storico dei vincitori
    private val storico = mutableListOf<String>()

    // metodo chiamato quando l'app si avvia
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // inizializza Activity
        setContentView(R.layout.activity_main) // collega layout XML

        // collega la ruota dal layout
        ruota = findViewById(R.id.ruota)

        // collega il testo del risultato
        risultato = findViewById(R.id.risultato)

        // collega il testo dello storico
        storicoView = findViewById(R.id.storico)

        // collega bottone GIRA
        gira = findViewById(R.id.gira)

        // collega bottone RESET
        reset = findViewById(R.id.reset)

        // carica suono iniziale
        siglaSound = MediaPlayer.create(this, R.raw.sigla)
        siglaSound.start() // avvia suono

        // quando premi il bottone GIRA
        gira.setOnClickListener {

            // se non ci sono nomi, non fa nulla
            if (ruota.nomi.isEmpty()) return@setOnClickListener

            // crea e avvia suono rotazione
            giraSound = MediaPlayer.create(this, R.raw.gira)
            giraSound.start()

            // genera numero casuale di giri completi
            val giri = Random.nextInt(5, 8)

            // aggiunge un angolo casuale finale
            val extra = Random.nextInt(0, 360)

            // aggiorna angolo totale della ruota
            angolo += (giri * 360) + extra

            // avvia animazione della rotazione
            ruota.animate()
                .rotation(angolo) // ruota fino al nuovo angolo
                .setDuration(2500) // durata animazione
                .setInterpolator(DecelerateInterpolator()) // rallenta alla fine
                .withEndAction { // quando finisce animazione

                    // ferma suono rotazione
                    if (giraSound.isPlaying) {
                        giraSound.stop()
                    }

                    // calcola chi ha vinto
                    val vincitore = calcolaVincitore()

                    // mostra risultato a schermo
                    risultato.text = "Ha vinto: $vincitore"

                    // suono vittoria
                    risultatoSound = MediaPlayer.create(this, R.raw.risultato)
                    risultatoSound.start()

                    // aggiunge vincitore allo storico
                    storico.add(vincitore)

                    // aggiorna testo storico
                    storicoView.text = "Storico: " + storico.joinToString(" → ")

                    // rimuove il vincitore dalla ruota
                    ruota.nomi.remove(vincitore)

                    // ridisegna la ruota
                    ruota.invalidate()

                    // mostra coriandoli
                    showCoriandoli()
                }
        }

        // quando premi RESET
        reset.setOnClickListener {

            angolo = 0f // reset angolo ruota

            ruota.nomi = listaIniziale.toMutableList() // ripristina nomi

            ruota.rotation = 0f // reset grafica rotazione
            ruota.invalidate() // ridisegna ruota

            risultato.text = "Premi Gira" // reset testo risultato

            storico.clear() // svuota storico
            storicoView.text = "Storico:" // reset testo
        }
    }

    // funzione che calcola il vincitore
    private fun calcolaVincitore(): String {

        val nomi = ruota.nomi // lista attuale nomi

        val step = 360f / nomi.size // grandezza di ogni fetta

        val normalized = (360f - (angolo % 360f)) % 360f // normalizza angolo

        val index = floor(normalized / step).toInt() // trova indice vincitore

        return nomi[index] // ritorna il nome vincitore
    }

    // funzione che crea effetto coriandoli
    private fun showCoriandoli() {

        // prende tutta la schermata
        val root = window.decorView.findViewById<FrameLayout>(android.R.id.content)

        // crea 40 coriandoli
        for (i in 0..40) {

            val coriandolo = TextView(this) // singolo elemento grafico

            coriandolo.text = "■" // forma del coriandolo

            coriandolo.textSize = (10..30).random().toFloat() // dimensione casuale

            coriandolo.setTextColor(
                Color.rgb(
                    (0..255).random(),
                    (0..255).random(),
                    (0..255).random()
                )
            ) // colore casuale

            coriandolo.x = (0..root.width).random().toFloat() // posizione X casuale

            coriandolo.y = -100f // parte dall’alto

            root.addView(coriandolo) // aggiunge a schermo

            ObjectAnimator.ofFloat(
                coriandolo,
                "translationY",
                0f,
                root.height + 300f
            ).apply {
                duration = (1500..3000).random().toLong() // durata animazione
                start() // avvia caduta
            }
        }
    }
}