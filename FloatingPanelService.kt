package com.galaxy.hyperboost

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

class FloatingPanelService : Service() {
    private var windowManager: WindowManager? = null
    private var panel: LinearLayout? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val green = Color.rgb(32, 216, 120)
        val box = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(22, 16, 22, 16)
            setBackgroundColor(Color.rgb(12, 20, 16))
        }
        val title = TextView(this).apply {
            text = "HYPERBOOST  •  PRO"
            textSize = 15f
            setTextColor(green)
        }
        val info = TextView(this).apply {
            text = "MODO: EQUILIBRADO\nDica: gráficos baixos ajudam na estabilidade."
            textSize = 12f
            setTextColor(Color.WHITE)
            setPadding(0, 8, 0, 8)
        }
        val close = TextView(this).apply {
            text = "FECHAR  ×"
            textSize = 13f
            setTextColor(green)
            setOnClickListener { stopSelf() }
        }
        box.addView(title); box.addView(info); box.addView(close)
        panel = box
        val type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.END
            x = 18; y = 140
        }
        windowManager?.addView(box, params)
    }

    override fun onDestroy() {
        panel?.let { view ->
            try { windowManager?.removeView(view) } catch (_: Exception) {}
        }
        panel = null
        super.onDestroy()
    }
}
