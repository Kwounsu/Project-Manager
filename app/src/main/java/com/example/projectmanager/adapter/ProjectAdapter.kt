package com.example.projectmanager.adapter

import ProjectList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R

class ProjectAdapter(var projectList: ProjectList) : RecyclerView.Adapter<ProjectAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val projectId: TextView = itemView.findViewById(R.id.text_id)
        val projectName: TextView = itemView.findViewById(R.id.text_projectName)
        val projectStatus: TextView = itemView.findViewById(R.id.text_projectStatus)
        val projectDesc: TextView = itemView.findViewById(R.id.text_projectDesc)
        val projectStartDate: TextView = itemView.findViewById(R.id.text_startDate)
        val projectEndDate: TextView = itemView.findViewById(R.id.text_endDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.project_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return projectList.projects.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projectList
        holder.projectId.text = project.projects.elementAt(position).id.toString()
        holder.projectName.text = project.projects.elementAt(position).projectname
        val statusNumber = project.projects.elementAt(position).projectstatus.toString()
        if (statusNumber == "1") holder.projectStatus.text = "Start new project"
        else if (statusNumber == "2") holder.projectStatus.text = "Not complete"
        else if (statusNumber == "3") holder.projectStatus.text = "Complete"
        else holder.projectStatus.text = "N/A"
        holder.projectDesc.text = project.projects.elementAt(position).projectdesc
        holder.projectStartDate.text = project.projects.elementAt(position).startdate
        holder.projectEndDate.text = project.projects.elementAt(position).endstart
    }
}