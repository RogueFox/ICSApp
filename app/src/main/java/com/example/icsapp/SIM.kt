package com.example.icsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_sim.*
import kotlinx.android.synthetic.main.activity_sim.homeButton
import kotlinx.android.synthetic.main.sim_entry_complex.view.*
import android.widget.AdapterView.OnItemSelectedListener
import kotlinx.android.synthetic.main.sim_entry.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import android.widget.SeekBar.OnSeekBarChangeListener as OnSeekBarChangeListener1

class SIM : AppCompatActivity() {

    fun refreshSchedule(days:ArrayList<Int>, startTimes:ArrayList<Int>, durations:ArrayList<Int>) {
        scheduleTable.removeAllViews()

        for(entry in 0 until days.size) {
            val currentDay = days[entry]
            val currentStartTime = startTimes[entry]
            val currentDuration = durations[entry]

            val row:TableRow = View.inflate(this, R.layout.sim_entry_complex, null) as TableRow

            ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item).also {
                arrayAdapter -> arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                row.day.adapter = arrayAdapter
            }

            row.day.setSelection(currentDay)
            row.day.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    days[entry] = position
                }

            }

            ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_item).also {
                    arrayAdapter -> arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                row.startTime.adapter = arrayAdapter
            }

            row.startTime.setSelection(currentStartTime)
            row.startTime.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    startTimes[entry] = position
                }
            }

            row.durationBar.max = 60
            row.durationBar.min = 0
            row.durationBar.progress = (currentDuration)
            row.duration.text = ("${row.durationBar.progress} min")
            row.durationBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    row.duration.text = "$progress min"
                    durations[entry] = progress
                }

                /**
                 * Notification that the user has started a touch gesture. Clients may want to use this
                 * to disable advancing the seekbar.
                 * @param seekBar The SeekBar in which the touch gesture began
                 */
                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                /**
                 * Notification that the user has finished a touch gesture. Clients may want to use this
                 * to re-enable advancing the seekbar.
                 * @param seekBar The SeekBar in which the touch gesture began
                 */
                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })

            scheduleTable.addView(row)
        }
    }

    fun checkDuplicates(days:ArrayList<Int>, startTimes:ArrayList<Int>): Boolean {
        for (entry in 0 until days.size) {
            for (checkEntry in 0 until days.size) {
                if (entry == checkEntry)
                    continue
                if (days[entry] == days[checkEntry] && startTimes[entry] == startTimes[checkEntry]) {
                    return true
                }
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sim)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainMenu ::class.java)
            startActivity(intent)
        }

        val days: ArrayList<Int>
        val startTimes: ArrayList<Int>
        val durations: ArrayList<Int>
        // Get schedule from intent extras
        if(!intent.getBooleanExtra("newSchedule", true)) {
            days = intent.getIntegerArrayListExtra("days")
            startTimes = intent.getIntegerArrayListExtra("startTimes")
            durations = intent.getIntegerArrayListExtra("durations")
        }
        else {
            days = ArrayList<Int>()
            startTimes = ArrayList<Int>()
            durations = ArrayList<Int>()
        }

        // Dynamically display schedule
        refreshSchedule(days, startTimes, durations)

        addNewButton.setOnClickListener {
            days.add(0)
            startTimes.add(12)
            durations.add(30)
            refreshSchedule(days, startTimes, durations)
        }

        confirmButton.setOnClickListener {
            if (!checkDuplicates(days, startTimes)) {
                Toast.makeText(this, "Submitting schedule...", Toast.LENGTH_SHORT).show()
                // Mon -> Sun = 0 -> 6
                // 0:00 -> 23:00 = 0 -> 23
                // TODO: Implement network call
            }
            else {
                Toast.makeText(this, "Submission failed!\nOverlapping entries...", Toast.LENGTH_LONG).show()
            }
        }

//        addNewButton.setOnClickListener {
//            val intent = Intent(this, ModifySchedule ::class.java)
//            intent.putExtra("newEntry", true)
//            startActivity(intent)
//        }
    }
}
