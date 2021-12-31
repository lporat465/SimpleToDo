package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils.writeLines
import org.apache.commons.io.IOUtils.writeLines
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //when user clicks on add button
        //findViewById<Button>(R.id.button).setOnClickListener{
        //    //code executed when user clicks button
        //}

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //1.remove item from list
                listOfTasks.removeAt(position)
                //2.notify adapter data has changed
                adapter.notifyDataSetChanged()

                saveItems()

            }

        }

        loadItems()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Set up button and input field so user can add to list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        //Get reference to button
        //Set an onclicklistener
        findViewById<Button>(R.id.button).setOnClickListener{
            //1.grab text user has inputted
            val userInputtedTask = inputTextField.text.toString()
            //2.add to list of tasks
            listOfTasks.add(userInputtedTask)

            //notify adapter data has changed
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //3.reset text field
            inputTextField.setText("")

            saveItems()
        }
    }

    //Save the data the user has inputted
    //By writing/reading from file

    fun getDataFile() : File{
        //Every line will represent a task in our list of tasks
        return File(filesDir, "data.txt")
    }


    //Create a method to get file we need

    //load the items by reading every file
    fun loadItems(){
        try{
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }


    }

    //Save items by writing into data file
    fun saveItems(){
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch(ioException: IOException){
            ioException.printStackTrace()
        }
    }
}