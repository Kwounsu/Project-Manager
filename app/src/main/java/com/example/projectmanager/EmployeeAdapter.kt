package com.example.projectmanager

import EmployeeList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeAdapter(var employeeList: EmployeeList) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val empID: TextView = itemView.findViewById(R.id.text_empId)
        val empFirstName: TextView = itemView.findViewById(R.id.text_empFirstName)
        val empLastName: TextView = itemView.findViewById(R.id.text_empLastName)
        val empEmail: TextView = itemView.findViewById(R.id.text_empEmail)
        val empMobile: TextView = itemView.findViewById(R.id.text_empMobile)
        val empDesignation: TextView = itemView.findViewById(R.id.text_empDesignation)
        val empDateOfJoining: TextView = itemView.findViewById(R.id.text_empDateOfJoining)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.employee_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return employeeList.employees.size
    }

    override fun onBindViewHolder(holder: EmployeeAdapter.ViewHolder, position: Int) {
        val employee = employeeList
        holder.empID.text = employee.employees.elementAt(position).empid.toString()
        holder.empFirstName.text = employee.employees.elementAt(position).empfirstname
        holder.empLastName.text = employee.employees.elementAt(position).emplastname
        holder.empEmail.text = employee.employees.elementAt(position).empemail
        holder.empMobile.text = employee.employees.elementAt(position).empmobile
        holder.empDesignation.text = employee.employees.elementAt(position).empdesignation
        holder.empDateOfJoining.text = employee.employees.elementAt(position).dateofjoining
    }
}