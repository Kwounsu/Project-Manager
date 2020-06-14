package com.example.projectmanager.adapter

import ProjectSubtaskList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R

class SubtaskAdapter(var projectSubtaskList: ProjectSubtaskList) : RecyclerView.Adapter<SubtaskAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subtaskId: TextView = itemView.findViewById(R.id.text_subtaskId)
        val subtasksTaskId: TextView = itemView.findViewById(R.id.text_subtasksTaskId)
        val subtasksProejctId: TextView = itemView.findViewById(R.id.text_subtasksProjectId)
        val subtaskName: TextView = itemView.findViewById(R.id.text_subtaskName)
        val subtaskStatus: TextView = itemView.findViewById(R.id.text_subtaskStatus)
        val subtaskDesc: TextView = itemView.findViewById(R.id.text_subtaskDesc)
        val subtaskStartDate: TextView = itemView.findViewById(R.id.text_subtaskStartDate)
        val subtaskEndDate: TextView = itemView.findViewById(R.id.text_subtaskEndDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.subtask_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return projectSubtaskList.projectSubtasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subtask = projectSubtaskList
        holder.subtaskId.text = subtask.projectSubtasks.elementAt(position).subtaskid.toString()
        holder.subtasksTaskId.text = subtask.projectSubtasks.elementAt(position).taskid.toString()
        holder.subtasksProejctId.text = subtask.projectSubtasks.elementAt(position).projectid.toString()
        holder.subtaskName.text = subtask.projectSubtasks.elementAt(position).subtaskname
        val statusNumber = subtask.projectSubtasks.elementAt(position).subtaskstatus.toString()
        if (statusNumber == "1") holder.subtaskStatus.text = "Start new project"
        else if (statusNumber == "2") holder.subtaskStatus.text = "Not complete"
        else if (statusNumber == "3") holder.subtaskStatus.text = "Complete"
        else holder.subtaskStatus.text = "N/A"
        holder.subtaskDesc.text = subtask.projectSubtasks.elementAt(position).subtaskdesc
        holder.subtaskStartDate.text = subtask.projectSubtasks.elementAt(position).startdate
        holder.subtaskEndDate.text = subtask.projectSubtasks.elementAt(position).endstart
    }
}