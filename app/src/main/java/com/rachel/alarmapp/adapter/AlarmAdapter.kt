package com.rachel.alarmapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rachel.alarmapp.R
import com.rachel.alarmapp.room.Alarm
import kotlinx.android.synthetic.main.row_reminder_alarm.view.*

class AlarmAdapter () : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {
    class AlarmViewHolder (val view: View) : RecyclerView.ViewHolder(view)


    var alarms = emptyList<Alarm>()

    fun setData(list: List<Alarm>){
        val alarmDiffutil = AlarmDiffutil(alarms, list)
        val alarmDiffutilResult = DiffUtil.calculateDiff(alarmDiffutil)
        this.alarms = list
        alarmDiffutilResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()


    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_reminder_alarm, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.view.time_alam_2.text = alarm.time
        holder.view.tv_date_alarm_2.text = alarm.date
        holder.view.tv_note.text = alarm.note

    }

    override fun getItemCount() = alarms.size




}