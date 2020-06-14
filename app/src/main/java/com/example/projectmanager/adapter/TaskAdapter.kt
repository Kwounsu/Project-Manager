package com.example.projectmanager.adapter

import ProjectTaskList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R

class TaskAdapter(var projectTaskList: ProjectTaskList) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskId: TextView = itemView.findViewById(R.id.text_taskId)
        val tasksProejctId: TextView = itemView.findViewById(R.id.text_tasksProjectId)
        val taskName: TextView = itemView.findViewById(R.id.text_taskName)
        val taskStatus: TextView = itemView.findViewById(R.id.text_taskStatus)
        val taskDesc: TextView = itemView.findViewById(R.id.text_taskDesc)
        val taskStartDate: TextView = itemView.findViewById(R.id.text_taskStartDate)
        val taskEndDate: TextView = itemView.findViewById(R.id.text_taskEndDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.task_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return projectTaskList.projectTasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = projectTaskList
        holder.taskId.text = task.projectTasks.elementAt(position).taskid.toString()
        holder.tasksProejctId.text = task.projectTasks.elementAt(position).projectid.toString()
        holder.taskName.text = task.projectTasks.elementAt(position).taskname
        val statusNumber = task.projectTasks.elementAt(position).taskstatus.toString()
        if (statusNumber == "1") holder.taskStatus.text = "Start new project"
        else if (statusNumber == "2") holder.taskStatus.text = "Not complete"
        else if (statusNumber == "3") holder.taskStatus.text = "Complete"
        else holder.taskStatus.text = "N/A"
        holder.taskDesc.text = task.projectTasks.elementAt(position).taskdesc
        holder.taskStartDate.text = task.projectTasks.elementAt(position).startdate
        holder.taskEndDate.text = task.projectTasks.elementAt(position).endstart
    }
}