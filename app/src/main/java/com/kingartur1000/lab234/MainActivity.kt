package com.kingartur1000.lab234

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvStatus: TextView
    private val TAG = "LevelMenuLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStatus = findViewById(R.id.tvStatus)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            val text = getString(R.string.tv_status_back)
            tvStatus.text = text
            Log.d(TAG, "Обработка клика: Нажата кнопка Назад")
        }

        // Присваиваем обрабочик клика (this) для всех 30 кнопок
        for (i in 1..30) {
            val resId = resources.getIdentifier("btn$i", "id", packageName)
            val button = findViewById<Button>(resId)
            button?.setOnClickListener(this)
        }
    }

    // Имплементация интерфейса View.OnClickListener
    override fun onClick(v: View?) {
        if (v is Button) {
            val buttonText = v.text.toString()
            val levelNum = buttonText.toIntOrNull() ?: return

            val statusText = getString(R.string.tv_status_level, levelNum)
            tvStatus.text = statusText

            // Логирование в Android Logcat
            Log.d(TAG, "Нажата кнопка уровня: $levelNum")
        }
    }
}