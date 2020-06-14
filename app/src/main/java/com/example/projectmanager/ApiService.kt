package com.example.projectmanager

import EmployeeList
import ForgotPassword
import MembersList
import Msg
import TaskPriority
import ProjectList
import ProjectSubtask
import ProjectSubtaskList
import ProjectTask
import ProjectTaskList
import SubtaskPriority
import User
import ViewTaskList
import ViewsubtasksList
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("pms_reg.php")
    fun register(
        @Query("first_name") first_name: String,
        @Query("last_name") last_name: String,
        @Query("email") email: String,
        @Query("mobile") mobile: String,
        @Query("password") password: String,
        @Query("company_size") company_size: String,
        @Query("your_role") your_role: String
    ): Observable<JsonObject>

    @GET("pms_login.php")
    fun register(
        @Query("email") email: String,
        @Query("password") password: String
    ): Observable<JsonObject>

    @GET("pms_forgot_pass.php")
    fun findPassword(
        @Query("email") email: String
    ): Observable<ForgotPassword>

    @GET("pms_projects.php")
    fun projectList(): Observable<ProjectList>

    @GET("pms_create_project.php")
    fun createProject(
        @Query("project_name") project_name: String,
        @Query("project_status") project_status: Int,
        @Query("project_desc") project_desc: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Observable<Msg>

    @GET("pms_edit_project.php")
    fun editProject(
        @Query("project_id") project_id: Int,
        @Query("project_name") project_name: String,
        @Query("project_status") project_status: Int,
        @Query("project_desc") project_desc: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Observable<Msg>

    @GET("pms_project_task_list.php")
    fun projectTaskList(): Observable<ProjectTaskList>

    @GET("pms_create_task.php")
    fun createTask(
        @Query("project_id") project_id: Int,
        @Query("task_name") task_name: String,
        @Query("task_status") task_status: Int,
        @Query("task_desc") task_desc: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Observable<Msg>

    @GET("pms_project_sub_task_list.php")
    fun projectSubtaskList(): Observable<ProjectSubtaskList>

    @GET("pms_create_sub_task.php")
    fun createSubtask(
        @Query("project_id") project_id: Int,
        @Query("task_id") task_id: Int,
        @Query("sub_task_name") task_name: String,
        @Query("sub_task_status") task_status: Int,
        @Query("sub_task_desc") task_desc: String,
        @Query("start_date") start_date: String,
        @Query("end_date") end_date: String
    ): Observable<Msg>

    @GET("pms_employee_list.php")
    fun employeeList(): Observable<EmployeeList>

    @GET("pms_create_project_team.php")
    fun assignProject(
        @Query("project_id") project_id: String,
        @Query("team_member_userid") team_member_userid: String,
        @Query("task_id") task_id: String,
        @Query("subtask_id") subtask_id: String
    ): Observable<Msg>

    @GET("pms_assign_task_project.php")
    fun assignTask(
        @Query("project_id") project_id: Int,
        @Query("task_id") task_id: Int,
        @Query("team_member_userid") team_member_userid: Int
    ): Observable<Msg>

    @GET("pms_assign_sub_task_project.php")
    fun assignSubtask(
        @Query("project_id") project_id: Int,
        @Query("task_id") task_id: Int,
        @Query("subtask_id") subtask_id: Int,
        @Query("team_member_userid") team_member_userid: Int
    ): Observable<Msg>

    @GET("pms_view_task.php")
    fun taskListByUser(
        @Query("user_id") user_id: String
    ): Observable<ViewTaskList>

    @GET("pms_view_subtask.php")
    fun subtaskListByUser(
        @Query("user_id") user_id: String,
        @Query("taskid") taskid: String
    ): Observable<ViewsubtasksList>

    @GET("pms_view_task_deatil.php")
    fun taskDetail(
        @Query("taskid") taskid: String,
        @Query("project_id") project_id: String
    ): Observable<ProjectTask>

    @GET("pms_view_sub_task_deatil.php")
    fun subtaskDetail(
        @Query("taskid") taskid: String,
        @Query("subtask_id") subtask_id: String,
        @Query("project_id") project_id: String
    ): Observable<ProjectSubtask>

    @GET("pms_team_task.php")
    fun taskTeamMember(
        @Query("taskid") taskid: String,
        @Query("projectid") projectid: String
    ): Observable<MembersList>

    @GET("pms_team_sub_task.php")
    fun subtaskTeamMember(
        @Query("taskid") taskid: String,
        @Query("subtaskid") subtaskid: String,
        @Query("projectid") projectid: String
    ): Observable<MembersList>

    @GET("pms_team_member_deatil.php")
    fun teamMemberDetail(
        @Query("memberuserid") memberuserid: String
    ): Observable<User>

    @GET("pms_edit_task_status.php")
    fun editTaskStatus(
        @Query("taskid") taskid: String,
        @Query("project_id") project_id: String,
        @Query("userid") userid: String,
        @Query("task_status") task_status: String
    ): Observable<Msg>

    @GET("pms_edit_sub_task_status.php")
    fun editSubtaskStatus(
        @Query("taskid") taskid: String,
        @Query("subtaskid") subtaskid: String,
        @Query("project_id") project_id: String,
        @Query("userid") userid: String,
        @Query("subtask_status") subtask_status: String
    ): Observable<Msg>

    @GET("pms_view_task_priority.php")
    fun taskPriority(
        @Query("taskid") taskid: String,
        @Query("project_id") project_id: String,
        @Query("userid") userid: String
    ): Observable<TaskPriority>

    @GET("pms_view_sub_task_priority.php")
    fun subtaskPriority(
        @Query("taskid") taskid: String,
        @Query("subtaskid") subtaskid: String,
        @Query("project_id") project_id: String,
        @Query("userid") userid: String
    ): Observable<SubtaskPriority>

    companion object {
        val BASE_URL = "http://rjtmobile.com/aamir/pms/android-app/"

        private val client = OkHttpClient
            .Builder()
            .build()

        private val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(ApiService::class.java)

        fun buildService(): ApiService {
            return retrofit
        }
    }
}