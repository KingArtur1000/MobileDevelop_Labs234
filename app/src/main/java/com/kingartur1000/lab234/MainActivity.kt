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
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var infoTextView: TextView
    private lateinit var dynamicContainer: LinearLayout  // вертикальный контейнер для рядов
    private var dynamicCounter = 21                      // начальный номер для новых уровней
    private var currentRow: LinearLayout? = null         // текущий горизонтальный ряд
    private var contextButton: Button? = null

    companion object {
        private const val TAG = "GameLevels"
        private const val BUTTONS_PER_ROW = 5
        private const val INITIAL_DYNAMIC_NUMBER = 21
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        infoTextView = findViewById(R.id.infoTextView)
        dynamicContainer = findViewById(R.id.dynamicContainer)

        // Назначаем обработчики для статических кнопок
        val buttonIds = intArrayOf(
            R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10,
            R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15,
            R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20
        )
        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener(this)
        }

        // Кнопка "Назад"
        findViewById<Button>(R.id.buttonBack).setOnClickListener { finish() }
    }

    // Обработчик для статических кнопок
    override fun onClick(v: View?) {
        if (v is Button) {
            val text = v.text.toString()
            val levelNumber = text.toIntOrNull() ?: 0
            infoTextView.text = getString(R.string.info_clicked, levelNumber)
            Log.d(TAG, "Нажата кнопка уровня: $levelNumber")
        }
    }

    // -------------------- Обычное меню --------------------
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

    // -------------------- Динамическое добавление кнопок (по 5 в ряд) --------------------
    private fun addDynamicButton() {
        // Если текущего ряда нет или он заполнен – создаём новый
        if (currentRow == null || currentRow!!.childCount >= BUTTONS_PER_ROW) {
            currentRow = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                // Добавляем отступ снизу для ряда
                setPadding(0, 0, 0, 8)
            }
            dynamicContainer.addView(currentRow)
        }

        // Создаём кнопку
        val newButton = Button(this).apply {
            text = getString(R.string.level_prefix) + dynamicCounter
            id = View.generateViewId()
            // Обработчик нажатия
            setOnClickListener {
                infoTextView.text = (it as Button).text
                Log.d(TAG, "Нажата динамическая кнопка: ${it.text}")
            }
            // Регистрация контекстного меню
            registerForContextMenu(this)
            // Равномерная ширина через weight
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
            )
            // Отступ справа для всех, кроме последнего
            if (currentRow!!.childCount < BUTTONS_PER_ROW - 1) {
                (layoutParams as LinearLayout.LayoutParams).marginEnd = 8
            }
        }

        currentRow!!.addView(newButton)
        dynamicCounter++

        Toast.makeText(this, R.string.toast_added, Toast.LENGTH_SHORT).show()
    }

    private fun clearDynamicButtons() {
        dynamicContainer.removeAllViews()   // удаляем все ряды
        currentRow = null                   // сбрасываем текущий ряд
        dynamicCounter = INITIAL_DYNAMIC_NUMBER // сбрасываем счётчик на 21
        Toast.makeText(this, R.string.toast_cleared, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Динамические кнопки очищены, счётчик сброшен")
    }

    // -------------------- Контекстное меню --------------------
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
                contextButton?.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.component_red)
                )
                true
            }
            R.id.context_green -> {
                contextButton?.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.component_green)
                )
                true
            }
            R.id.context_blue -> {
                contextButton?.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.component_blue)
                )
                true
            }
            R.id.context_yellow -> {
                contextButton?.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.component_yellow)
                )
                true
            }
            R.id.context_orange -> {
                contextButton?.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.component_orange)
                )
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}