package com.rachel.alarmapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rachel.alarmapp.adapter.AlarmAdapter
import com.rachel.alarmapp.room.AlarmDB
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*



class MainActivity : AppCompatActivity() {

    private lateinit var alarmAdapter: AlarmAdapter
    private lateinit var alarmReceiver: AlarmReceiver
    private val db by lazy { AlarmDB(this) }

    // perubahan onStart menjadi onResume dilakukan untuk menghilangkan bug penambahan alarm
    // sehingga data akan diperbarui meskpiun MainActivity ini sebelumnya dalam kondisi onPause
    override fun onResume() {
        super.onResume()
        // Menghapus CoroutineScope karena LiveData tidak dapat berjalan di background thread.
        // LiveData digunakan agar recyclerview dapat memperbarui data secara langsung
        // tanpa harus melalui lifecycle onResume ataupun onCreate (memulai ulang activity)
        db.alarmDao().getAlarm().observe(this@MainActivity) {
            alarmAdapter.setData(it)
            Log.d("MainActivity", "dbresponse: $it")
        }
    }

    //Berfungsi untuk insialisasi date, time dan recyclerview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alarmReceiver = AlarmReceiver()

        initTimeToday()
        initDateToday()
        initAlarmType()
        initRecyclerView()
    }

    private fun initDateToday() {
        val dateNow: Date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())
        val formattedDate: String = dateFormat.format(dateNow)

        tv_date_today.text = formattedDate
    }


    private fun initTimeToday() {
        val timeNow = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime = timeFormat.format(timeNow.time)

        tv_time_today.text = formattedTime
    }


    private fun initAlarmType() {
        btn_set_time.setOnClickListener {
            startActivity(Intent(this, SetAlarm::class.java))
        }

    }

    private fun initRecyclerView() {
        alarmAdapter = AlarmAdapter()
        rv_reminder_alarm.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = alarmAdapter
        }
    }
}

