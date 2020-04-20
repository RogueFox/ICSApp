package com.example.icsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        this.tcmButton.setOnClickListener {
            val intent = Intent(this, TCM ::class.java)
            startActivity(intent)
        }

        this.simButton.setOnClickListener {
            val intent = Intent(this, SIM ::class.java)
            // TODO: Implement checking for existing schedule
            intent.putExtra("newSchedule", true)
            startActivity(intent)
        }
    }
}
