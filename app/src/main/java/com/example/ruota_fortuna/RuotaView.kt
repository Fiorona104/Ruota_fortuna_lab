
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


    // nomi dentro la ruota
    var nomi = mutableListOf("Luca", "Marco", "Giulia", "Sara", "Andrea", "Matteo")


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        if (nomi.isEmpty()) return


        val cx = width / 2f
        val cy = height / 2f
        val r = min(cx, cy) - 20


        val step = 360f / nomi.size


        for (i in nomi.indices) {


            paint.color = if (i % 2 == 0) Color.YELLOW else Color.CYAN


            val start = i * step


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


            val angle = Math.toRadians((start + step / 2).toDouble())


            val x = cx + (r / 1.5 * cos(angle)).toFloat()
            val y = cy + (r / 1.5 * sin(angle)).toFloat()


            canvas.drawText(nomi[i], x, y, textPaint)
        }
    }
}
