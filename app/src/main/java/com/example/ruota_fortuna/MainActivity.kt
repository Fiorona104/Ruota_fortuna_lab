// package del progetto
package com.example.ruota_fortuna


// import per gestione audio
import android.media.MediaPlayer


// import base Android
import android.os.Bundle


// import animazioni
import android.view.animation.DecelerateInterpolator


// import UI elementi
import android.widget.Button
import android.widget.TextView
import android.widget.FrameLayout


// import colori
import android.graphics.Color


// import animazioni oggetti
import android.animation.ObjectAnimator


// activity principale Android
import androidx.appcompat.app.AppCompatActivity


// numeri casuali
import kotlin.random.Random


// funzione per arrotondare
import kotlin.math.floor




// classe principale dell'app
class MainActivity : AppCompatActivity() {


    // collegamento alla ruota grafica
    private lateinit var ruota: RuotaView


    // testo che mostra il risultato
    private lateinit var risultato: TextView


    // testo storico dei vincitori
    private lateinit var storicoView: TextView


    // bottone per far girare la ruota
    private lateinit var gira: Button


    // bottone per resettare la ruota
    private lateinit var reset: Button


    // suono sigla iniziale
    private lateinit var siglaSound: MediaPlayer


    // suono quando gira la ruota
    private lateinit var giraSound: MediaPlayer


    // suono quando esce il risultato
    private lateinit var risultatoSound: MediaPlayer


    // angolo totale della rotazione
    private var angolo = 0f


    // lista iniziale dei nomi nella ruota
    private val listaIniziale = mutableListOf(
        "Luca",
        "Marco",
        "Giulia",
        "Sara",
        "Andrea",
        "Matteo"
    )


    // lista che salva lo storico dei vincitori
    private val storico = mutableListOf<String>()




    // funzione che parte quando si apre l'app
    override fun onCreate(savedInstanceState: Bundle?) {


        // inizializza activity
        super.onCreate(savedInstanceState)


        // collega il layout grafico
        setContentView(R.layout.activity_main)


        // collega la ruota dal layout
        ruota = findViewById(R.id.ruota)


        // collega il testo risultato
        risultato = findViewById(R.id.risultato)


        // collega lo storico
        storicoView = findViewById(R.id.storico)


        // collega bottone gira
        gira = findViewById(R.id.gira)


        // collega bottone reset
        reset = findViewById(R.id.reset)


        // carica e avvia la sigla iniziale
        siglaSound = MediaPlayer.create(this, R.raw.sigla)
        siglaSound.start()




        // evento click bottone GIRA
        gira.setOnClickListener {


            // controllo che ci siano nomi nella ruota
            if (ruota.nomi.isEmpty()) return@setOnClickListener


            // crea e avvia suono della ruota
            giraSound = MediaPlayer.create(this, R.raw.gira)
            giraSound.start()


            // genera numero di giri casuale
            val giri = Random.nextInt(5, 8)


            // genera angolo finale casuale
            val extra = Random.nextInt(0, 360)


            // aggiorna angolo totale
            angolo += (giri * 360) + extra


            // anima la rotazione della ruota
            ruota.animate()
                .rotation(angolo)
                .setDuration(2500)
                .setInterpolator(DecelerateInterpolator())


                // quando la rotazione finisce
                .withEndAction {


                    // ferma suono della ruota
                    if (giraSound.isPlaying) {
                        giraSound.stop()
                    }


                    // calcola il vincitore
                    val vincitore = calcolaVincitore()


                    // mostra il risultato a schermo
                    risultato.text = "Ha vinto: $vincitore"


                    // crea e avvia suono risultato
                    risultatoSound =
                        MediaPlayer.create(this, R.raw.risultato)
                    risultatoSound.start()


                    // aggiunge il vincitore allo storico
                    storico.add(vincitore)


                    // aggiorna il testo dello storico
                    storicoView.text =
                        "Storico: " + storico.joinToString(" → ")


                    // rimuove il vincitore dalla ruota
                    ruota.nomi.remove(vincitore)


                    // aggiorna graficamente la ruota
                    ruota.invalidate()


                    // avvia i coriandoli
                    showConfetti()
                }
        }




        // evento click bottone RESET
        reset.setOnClickListener {


            // reset angolo ruota
            angolo = 0f


            // ripristina lista iniziale dei nomi
            ruota.nomi = listaIniziale.toMutableList()


            // reset rotazione grafica
            ruota.rotation = 0f


            // aggiorna grafica ruota
            ruota.invalidate()


            // reset testo risultato
            risultato.text = "Premi Gira"


            // svuota storico
            storico.clear()


            // aggiorna testo storico
            storicoView.text = "Storico:"
        }
    }




    // funzione che calcola il vincitore in base all'angolo
    private fun calcolaVincitore(): String {


        // lista nomi attuali
        val nomi = ruota.nomi


        // dimensione di ogni spicchio della ruota
        val step = 360f / nomi.size


        // normalizza l'angolo tra 0 e 360 gradi
        val normalized = (360f - (angolo % 360f)) % 360f


        // calcola indice del vincitore
        val index = floor(normalized / step).toInt()


        // ritorna il nome vincitore
        return nomi[index]
    }




    // funzione che crea i coriandoli animati
    private fun showConfetti() {


        // prende il layout principale dell'app
        val root =
            window.decorView.findViewById<FrameLayout>(android.R.id.content)


        // crea più coriandoli
        for (i in 0..40) {


            // crea un elemento grafico tipo testo
            val confetto = TextView(this)


            // simbolo del coriandolo
            confetto.text = "■"


            // dimensione casuale del coriandolo
            confetto.textSize = (10..30).random().toFloat()


            // colore casuale del coriandolo
            confetto.setTextColor(
                Color.rgb(
                    (0..255).random(),
                    (0..255).random(),
                    (0..255).random()
                )
            )


            // posizione orizzontale casuale
            confetto.x = (0..root.width).random().toFloat()


            // posizione iniziale sopra lo schermo
            confetto.y = -100f


            // aggiunge il coriandolo alla schermata
            root.addView(confetto)


            // animazione caduta verso il basso
            ObjectAnimator.ofFloat(
                confetto,
                "translationY",
                0f,
                root.height + 300f
            ).apply {


                // durata casuale animazione
                duration = (1500..3000).random().toLong()


                // avvia animazione
                start()
            }
        }
    }
}





