package com.galaxy.hyperboost

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private val green = Color.rgb(32, 216, 120)
    private val bg = Color.rgb(8, 13, 11)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(28, 48, 28, 28)
            setBackgroundColor(bg)
        }
        fun text(value: String, size: Float, color: Int) = TextView(this).apply {
            text = value; textSize = size; setTextColor(color)
            setPadding(0, 12, 0, 12)
        }
        root.addView(text("GALAXY HYPERBOOST", 25f, green))
        root.addView(text("PRO  •  GAME CONTROL CENTER", 13f, Color.LTGRAY))
        root.addView(text("Perfil: desempenho equilibrado", 17f, Color.WHITE))
        root.addView(text("Este app oferece atalhos e um painel flutuante.\nEle não desbloqueia FPS nem aumenta a potência física do aparelho.", 14f, Color.LTGRAY))
        val overlay = Button(this).apply {
            text = "ATIVAR PAINEL FLUTUANTE"
            setTextColor(Color.BLACK)
            setBackgroundColor(green)
            setOnClickListener {
                if (!Settings.canDrawOverlays(this@MainActivity)) {
                    val i = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName"))
                    startActivity(i)
                } else {
                    startService(Intent(this@MainActivity, FloatingPanelService::class.java))
                }
            }
        }
        root.addView(overlay)
        val stop = Button(this).apply {
            text = "FECHAR PAINEL"
            setOnClickListener { stopService(Intent(this@MainActivity, FloatingPanelService::class.java)) }
        }
        root.addView(stop)
        root.addView(text("Dica: feche apps que não estiver usando e mantenha os gráficos do Roblox no baixo.", 14f, green))
        setContentView(root)
    }

    override fun onResume() {
        super.onResume()
        // O usuário pode voltar das permissões e tocar novamente em Ativar.
    }
}
