package com.kingartur1000.lab234

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var infoTextView: TextView
    private lateinit var dynamicContainer: LinearLayout
    private var dynamicCounter = 21          // для нумерации новых кнопок
    private var contextButton: Button? = null // для контекстного меню

    companion object {
        private const val TAG = "GameLevels"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        infoTextView = findViewById(R.id.infoTextView)
        dynamicContainer = findViewById(R.id.dynamicContainer)

        // Назначаем обработчик для 20 статических кнопок
        val buttonIds = intArrayOf(
            R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10,
            R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15,
            R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20
        )
        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener(this)
        }

        // Кнопка "Назад" – закрываем Activity
        findViewById<Button>(R.id.buttonBack).setOnClickListener { finish() }
    }

    // Обработчик для всех статических кнопок
    override fun onClick(v: View?) {
        if (v is Button) {
            val text = v.text.toString()
            val levelNumber = text.toIntOrNull() ?: 0
            // Обновляем TextView (ресурс)
            infoTextView.text = getString(R.string.info_clicked, levelNumber)
            // Логируем
            Log.d(TAG, "Нажата кнопка уровня: $levelNumber")
        }
    }

    // -------------------- Обычное меню (опции) --------------------
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                addDynamicButton()
                true
            }
            R.id.menu_clear -> {
                clearDynamicButtons()
                true
            }
            R.id.menu_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // -------------------- Динамическое добавление кнопок --------------------
    private fun addDynamicButton() {
        val newButton = Button(this).apply {
            text = getString(R.string.level_prefix) + dynamicCounter
            id = View.generateViewId()
            // Обработчик для динамической кнопки
            setOnClickListener {
                infoTextView.text = (it as Button).text
                Log.d(TAG, "Нажата динамическая кнопка: ${it.text}")
            }
            // Регистрируем контекстное меню
            registerForContextMenu(this)
        }

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { setMargins(0, 8, 0, 8) }
        newButton.layoutParams = params
        dynamicContainer.addView(newButton)

        Toast.makeText(this, R.string.toast_added, Toast.LENGTH_SHORT).show()
        dynamicCounter++
    }

    private fun clearDynamicButtons() {
        dynamicContainer.removeAllViews()
        Toast.makeText(this, R.string.toast_cleared, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Динамические кнопки очищены")
    }

    // -------------------- Контекстное меню для динамических кнопок --------------------
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v is Button) {
            contextButton = v
            menuInflater.inflate(R.menu.context_menu, menu)
            menu?.setHeaderTitle(R.string.context_change_color)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.context_red -> {
                contextButton?.setBackgroundColor(0xFFFF0000.toInt())
                true
            }
            R.id.context_green -> {
                contextButton?.setBackgroundColor(0xFF00FF00.toInt())
                true
            }
            R.id.context_blue -> {
                contextButton?.setBackgroundColor(0xFF0000FF.toInt())
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}