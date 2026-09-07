package com.kingartur1000.lab234

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Обработка кнопки "Назад"
        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            Toast.makeText(this, "Нажата кнопка Назад", Toast.LENGTH_SHORT).show()
        }

        // Подключаем слушатели нажатий для всех 30 кнопок
        for (i in 1..30) {
            val resId = resources.getIdentifier("btn$i", "id", packageName)
            val button = findViewById<Button>(resId)

            button?.setOnClickListener {
                Toast.makeText(this, "Выбран уровень $i", Toast.LENGTH_SHORT).show()
            }
        }
    }
}