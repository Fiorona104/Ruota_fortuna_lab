package com.example.ruota_fortuna // definisce il nome del pacchetto (cartella logica del progetto Android)

import android.content.Context // serve per accedere a risorse del sistema Android (es. colori, file, ecc.)
import android.graphics.Canvas // oggetto su cui disegniamo la ruota
import android.graphics.Color // classe che contiene i colori
import android.graphics.Paint // serve per definire stile, colore e caratteristiche del disegno
import android.util.AttributeSet // permette di usare questa View anche da XML
import android.view.View // classe base per creare elementi grafici personalizzati

import kotlin.math.cos // funzione matematica per calcolare coordinate X su cerchio
import kotlin.math.min // restituisce il valore più piccolo (usato per il raggio)
import kotlin.math.sin // funzione matematica per calcolare coordinate Y su cerchio

// classe personalizzata che crea una ruota grafica
class RuotaView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    // lista dei nomi che verranno mostrati nei segmenti della ruota
    var nomi = mutableListOf("Luca", "Marco", "Giulia", "Sara", "Andrea", "Matteo")

    // Paint usato per disegnare i settori della ruota
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG) // anti alias rende i bordi più lisci

    // Paint usato per scrivere il testo (nomi)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK // colore del testo
        textSize = 40f // dimensione del testo
        textAlign = Paint.Align.CENTER // centra il testo rispetto al punto
    }

    // metodo chiamato automaticamente ogni volta che la view deve essere disegnata
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas) // chiama il metodo della classe madre

        // se la lista è vuota, non disegna nulla
        if (nomi.isEmpty()) return

        // trova il centro della view (orizzontale e verticale)
        val cx = width / 2f // centro X
        val cy = height / 2f // centro Y

        // calcola il raggio della ruota (metà dello spazio disponibile)
        val r = min(cx, cy) - 20 // -20 per lasciare margine

        // calcola quanto è grande ogni fetta della ruota in gradi
        val step = 360f / nomi.size

        // ciclo che disegna ogni segmento della ruota
        for (i in nomi.indices) {

            // alterna colori tra giallo e azzurro
            paint.color = if (i % 2 == 0) Color.YELLOW else Color.CYAN

            // calcola angolo di partenza del segmento
            val start = i * step

            // disegna il settore della ruota (tipo fetta di pizza)
            canvas.drawArc(
                cx - r, // lato sinistro del cerchio
                cy - r, // lato alto del cerchio
                cx + r, // lato destro del cerchio
                cy + r, // lato basso del cerchio
                start,  // angolo iniziale della fetta
                step,   // ampiezza della fetta
                true,   // chiude il segmento verso il centro
                paint   // stile del disegno
            )

            // calcola angolo centrale della fetta
            val angle = Math.toRadians((start + step / 2).toDouble())

            // calcola posizione X del testo dentro la fetta
            val x = cx + (r / 1.5 * cos(angle)).toFloat()

            // calcola posizione Y del testo dentro la fetta
            val y = cy + (r / 1.5 * sin(angle)).toFloat()

            // disegna il nome al centro della fetta
            canvas.drawText(nomi[i], x, y, textPaint)
        }
    }
}