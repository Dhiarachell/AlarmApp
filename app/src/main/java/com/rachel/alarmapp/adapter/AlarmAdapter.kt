package com.rachel.alarmapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rachel.alarmapp.R
import com.rachel.alarmapp.room.Alarm

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
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_reminder_alarm, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.view.item_time_alarm.text = alarm.time
        holder.view.item_time_alarm.text = alarm.date
        holder.view.item_time_alarm.text = alarm.note

        when (alarm.type){
            0 -> holder.view.item_img_one_time.loadImageDrawable(holder.view.context)
            0 -> holder.view.item_img_one_time.loadImageDrawable(holder.view.context)

        }
    }

    override fun getItemCount() = alarms.size

    private fun ImageView.loadImageDrawable(context: Context, drawable: Int){
        Glide.with(context).load(drawable).into(this)
    }



}