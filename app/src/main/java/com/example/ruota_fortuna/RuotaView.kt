package com.example.ruota_fortuna

import android.content.Context   // Permette di accedere alle risorse del sistema Android
import android.graphics.Canvas   // Usato per disegnare elementi grafici sullo schermo
import android.graphics.Color    // Contiene i colori predefiniti e la gestione dei colori
import android.graphics.Paint    // Serve per definire stile, colore e proprietà del disegno
import android.util.AttributeSet // Permette di usare la View anche da XML
import android.view.View         // Classe base per creare componenti grafici personalizzati


import kotlin.math.cos           // Calcola il coseno, per coordinate circolari
import kotlin.math.min           // Restituisce il valore minimo, per il raggio della ruota
import kotlin.math.sin           // Calcola il seno, per coordinate circolari

// Classe che disegna una ruota sullo schermo
class RuotaView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    // Lista dei nomi da inserire nei segmenti della ruota
    var nomi = mutableListOf("Luca", "Marco", "Giulia", "Sara", "Andrea", "Matteo")

    // Paint principale per colorare i segmenti
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Paint per il testo dei nomi, nero, centrato e con dimensione 40
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }

    // Metodo principale che disegna la ruota
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Se non ci sono nomi, non disegnare nulla
        if (nomi.isEmpty()) return

        // Calcolo del centro della ruota
        val cx = width / 2f
        val cy = height / 2f

        // Raggio della ruota, leggermente più piccolo della metà della dimensione minore
        val r = min(cx, cy) - 20

        // Angolo per ogni segmento della ruota
        val step = 360f / nomi.size

        // Ciclo su tutti i nomi per disegnare i segmenti
        for (i in nomi.indices) {

            // Colori alternati per i segmenti
            paint.color = if (i % 2 == 0) Color.YELLOW else Color.CYAN

            // Angolo di partenza del segmento
            val start = i * step

            // Disegna l'arco (segmento della ruota)
            canvas.drawArc(
                cx - r, // sinistra
                cy - r, // sopra
                cx + r, // destra
                cy + r, // sotto
                start,  // angolo iniziale
                step,   // ampiezza dell'arco
                true,   // "useCenter" -> chiude il segmento fino al centro
                paint   // paint con colore
            )

            // Calcolo della posizione del testo al centro del segmento
            val angle = Math.toRadians((start + step / 2).toDouble())
            val x = cx + (r / 1.5 * cos(angle)).toFloat() // coordinata X
            val y = cy + (r / 1.5 * sin(angle)).toFloat() // coordinata Y

            // Disegna il nome sul segmento
            canvas.drawText(nomi[i], x, y, textPaint)
        }
    }
}