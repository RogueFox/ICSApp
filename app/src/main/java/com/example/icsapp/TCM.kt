package com.example.icsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tcm.*

class TCM : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tcm)

        val dataMngr = DataManager()

        // Timer Bar values
        timerBar.max = 60
        timerBar.min = 1

        timerBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            /**
             * Notification that the progress level has changed. Clients can use the fromUser parameter
             * to distinguish user-initiated changes from those that occurred programmatically.
             *
             * @param seekBar The SeekBar whose progress has changed
             * @param progress The current progress level. This will be in the range min..max where min
             * and max were set by [ProgressBar.setMin] and
             * [ProgressBar.setMax], respectively. (The default values for
             * min is 0 and max is 100.)
             * @param fromUser True if the progress change was initiated by the user.
             */
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                timerDisplayValue.text = progress.toString()
            }

            /**
             * Notification that the user has started a touch gesture. Clients may want to use this
             * to disable advancing the seekbar.
             * @param seekBar The SeekBar in which the touch gesture began
             */
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            /**
             * Notification that the user has finished a touch gesture. Clients may want to use this
             * to re-enable advancing the seekbar.
             * @param seekBar The SeekBar in which the touch gesture began
             */
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        homeButton.setOnClickListener {
            val intent = Intent(this, MainMenu ::class.java)
            startActivity(intent)
        }

        confirmButton.setOnClickListener {
            dataMngr.sendTCMdata(timerBar.progress)
            val toast = Toast.makeText(this, "Submitted Timer", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}
