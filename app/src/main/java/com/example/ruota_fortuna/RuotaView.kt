package com.example.ruota_fortuna

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

// classe che disegna la ruota
class RuotaView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    // lista dei nomi presenti nella ruota
    var nomi = mutableListOf("Luca", "Marco", "Giulia", "Sara", "Andrea", "Matteo")

    // pennello per disegnare gli spicchi
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // pennello per scrivere i nomi
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }

    // metodo che disegna la ruota
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // se la lista è vuota non disegna nulla
        if (nomi.isEmpty()) return

        // centro della ruota
        val cx = width / 2f
        val cy = height / 2f

        // raggio della ruota
        val r = min(cx, cy) - 20

        // dimensione di ogni spicchio
        val step = 360f / nomi.size

        // ciclo per disegnare ogni spicchio
        for (i in nomi.indices) {

            // colori alternati
            paint.color = if (i % 2 == 0) Color.YELLOW else Color.CYAN

            val start = i * step

            // disegno dello spicchio
            canvas.drawArc(
                cx - r,
                cy - r,
                cx + r,
                cy + r,
                start,
                step,
                true,
                paint
            )

            // calcolo posizione del testo
            val angle = Math.toRadians((start + step / 2).toDouble())

            val x = cx + (r / 1.5 * cos(angle)).toFloat()
            val y = cy + (r / 1.5 * sin(angle)).toFloat()

            // scrive il nome nello spicchio
            canvas.drawText(nomi[i], x, y, textPaint)
        }
    }
}