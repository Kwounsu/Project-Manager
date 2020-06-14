package com.example.projectmanager.adapter

import MembersList
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R
import com.example.projectmanager.TeamMemberDetailActivity

class UserMemberTaskAdapter(var memberList: MembersList) : RecyclerView.Adapter<UserMemberTaskAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val assignId: TextView = itemView.findViewById(R.id.text_assignId)
        val projectId: TextView = itemView.findViewById(R.id.text_projectId)
        val taskId: TextView = itemView.findViewById(R.id.text_taskId)
        val userId: TextView = itemView.findViewById(R.id.text_userId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.task_member_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return memberList.members.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val member = memberList
        holder.assignId.text = member.members.elementAt(position).assignid.toString()
        holder.projectId.text = member.members.elementAt(position).projectid.toString()
        holder.taskId.text = member.members.elementAt(position).taskid.toString()
        holder.userId.text = member.members.elementAt(position).userid.toString()

        holder.itemView.setOnClickListener {
            Log.d("Retrofit","Clicked Task " + holder.taskId.text)
            val intent = Intent(holder.taskId.context, TeamMemberDetailActivity::class.java)
            intent.putExtra("userId", holder.userId.text.toString())
            holder.taskId.context.startActivity(intent)
        }
    }
}