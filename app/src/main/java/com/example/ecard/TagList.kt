package com.example.ecard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TagList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_list)
        val addButton : FloatingActionButton = findViewById(R.id.AddTagButton)
        addButton.setOnClickListener {
            Log.v("Tag List", "Add Tag Button Clicked!")
            startActivity(Intent(this, MainScreen::class.java))
        }
    }
}