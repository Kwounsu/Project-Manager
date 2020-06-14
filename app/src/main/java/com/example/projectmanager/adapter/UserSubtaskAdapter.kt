package com.example.projectmanager.adapter

import ViewsubtasksList
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R
import com.example.projectmanager.UserSubtaskDetailActivity

class UserSubtaskAdapter(var subtaskList: ViewsubtasksList, userId: String) : RecyclerView.Adapter<UserSubtaskAdapter.ViewHolder>() {
    val userId = userId

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tasksProjectId: TextView = itemView.findViewById(R.id.text_userProjectId)
        val taskId: TextView = itemView.findViewById(R.id.text_userTaskId)
        val subtaskId: TextView = itemView.findViewById(R.id.text_userSubtaskId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.user_subtask_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subtaskList.viewsubtasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subtask = subtaskList
        holder.tasksProjectId.text = subtask.viewsubtasks.elementAt(position).projectid.toString()
        holder.taskId.text = subtask.viewsubtasks.elementAt(position).taskid.toString()
        holder.subtaskId.text = subtask.viewsubtasks.elementAt(position).subtaskid.toString()
        holder.itemView.setOnClickListener {
            Log.d("Retrofit","Clicked Task " + holder.taskId.text)
            val intent = Intent(holder.subtaskId.context, UserSubtaskDetailActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("taskId", holder.taskId.text.toString())
            intent.putExtra("subtaskId", holder.subtaskId.text.toString())
            intent.putExtra("projectId", holder.tasksProjectId.text.toString())
            holder.subtaskId.context.startActivity(intent)
        }
    }
}