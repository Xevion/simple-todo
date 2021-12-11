package com.embers.simpleto_do

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val taskList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Button is by default disabled (no text in textbox)
        val button = findViewById<Button>(R.id.button)
        button.isEnabled = false

        // Setup RecyclerView
        val editText = findViewById<EditText>(R.id.editTodoText)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = TaskItemAdapter(taskList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Disable add task button when text is not inside EditText
        findViewById<EditText>(R.id.editTodoText).setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            // Read only event after the action has been processed in the textbox
            if (event.action == KeyEvent.ACTION_UP)
                button.isEnabled = editText.text.isNotBlank()

            false
        })

        findViewById<Button>(R.id.button).setOnClickListener {
            // Check that the task name has text in it
            if (editText.text.isBlank()) return@setOnClickListener

            // Add task name to task list
            taskList.add(editText.text.toString())

            // Clear text box
            editText.text.clear()

            // Notify the adapter that data has changed
            adapter.notifyItemInserted(taskList.size - 1)
        }
    }
}