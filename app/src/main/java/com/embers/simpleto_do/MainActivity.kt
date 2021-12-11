package com.embers.simpleto_do

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    private val TAG: String = this::class.java.name

    private var taskList = mutableListOf<String>()
    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : TaskItemAdapter
    private lateinit var button: Button

    private fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    private fun saveData(data: List<String> = taskList) {
        val file: File = getDataFile()
        try {
            FileUtils.writeLines(file, data)
            Log.i(TAG, "Successfully wrote ${taskList.size} tasks to ${file.absolutePath}")
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

    /**
     * Load data from the relevant data file.
     *
     */
    private fun loadData() {
        val file: File = getDataFile()
        try {
            taskList = FileUtils.readLines(file, Charset.defaultCharset())
            Log.i(TAG, "Successfully read ${taskList.size} tasks from ${file.absolutePath}")
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

    /**
     * Updates the enabled state of the 'Add' button depending on whether the text field has text.
     *
     */
    private fun refreshButton() {
        button.isEnabled = editText.text.isNotBlank()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        /**
         * Callback to remove a task at a given location in the list (on long click action)
         */
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                taskList.removeAt(position)
                adapter.notifyItemRemoved(position)
                saveData()
            }
        }

        // Setup RecyclerView
        editText = findViewById<EditText>(R.id.editTodoText)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(taskList, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        button = findViewById(R.id.button)

        refreshButton()

        // Disable add task button when text is not inside EditText
        findViewById<EditText>(R.id.editTodoText).setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            // Read only event after the action has been processed in the textbox
            if (event.action == KeyEvent.ACTION_UP)
                refreshButton()

            false
        })

        findViewById<Button>(R.id.button).setOnClickListener {
            // Check that the task name has text in it
            if (editText.text.isBlank()) return@setOnClickListener

            taskList.add(editText.text.toString()) // Add task name to task list
            editText.text.clear() // Clear text box
            refreshButton() // Update button accordingly
            adapter.notifyItemInserted(taskList.size - 1) // Notify the adapter that data has changed
            saveData()
        }
    }
}