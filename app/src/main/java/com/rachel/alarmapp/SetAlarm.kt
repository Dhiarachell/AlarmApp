package com.rachel.alarmapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rachel.alarmapp.databinding.ActivityMainBinding
import com.rachel.alarmapp.fragment.DatePickerFragment
import com.rachel.alarmapp.fragment.TimePickerFragment
import com.rachel.alarmapp.room.Alarm
import com.rachel.alarmapp.room.AlarmDB
import kotlinx.android.synthetic.main.activity_set_alarm.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.text.SimpleDateFormat
import java.util.*

class SetAlarm : AppCompatActivity(), View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    private  var binding: ActivityMainBinding? = null
    private lateinit var alarmReceiver: AlarmReceiver
    val db by lazy { AlarmDB(this) }

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_set_alarm)

        btn_set_time_repeating_2.setOnClickListener(this)
        btn_add_set_repeating_alarm.setOnClickListener(this)
        btn_cancel.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_set_time_repeating_2 -> {
                val timePickerFragmentOnTime = TimePickerFragment()
                timePickerFragmentOnTime.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
            }
            R.id.btn_add_set_repeating_alarm -> {
                val onceTime = tv_repeating_time.text.toString()
                val onceMessage = et_note_repeating.text.toString()

                alarmReceiver.setRepeatingAlarm(
                    this, AlarmReceiver.TYPE_REPEATING,
                    onceTime,
                    onceMessage
                )

                CoroutineScope(Dispatchers.IO).launch {
                    db.alarmDao().addAlarm(
                        Alarm(0,
                            onceTime,
                            "Repeating Alarm",
                            onceMessage,
                            AlarmReceiver.TYPE_REPEATING)
                    )
                    finish()
                }
            }
            R.id.btn_cancel -> {
                onBackPressed()
                finish()
            }
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, mount: Int, dayOfMount: Int) {
        TODO("Not yet implemented")
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val timeFormatRepeating = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            TIME_PICKER_REPEAT_TAG -> tv_repeating_time.text =
                timeFormatRepeating.format(calendar.time)
            else -> {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}