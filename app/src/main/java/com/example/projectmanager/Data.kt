import com.google.gson.annotations.SerializedName

data class Login (
	@SerializedName("msg") val msg : String,
	@SerializedName("userid") val userid : Int,
	@SerializedName("userfirstname") val userfirstname : String,
	@SerializedName("userlastname") val userlastname : String,
	@SerializedName("useremail") val useremail : String,
	@SerializedName("appapikey ") val appapiappapikey : String
)

data class ForgotPassword (
	@SerializedName("msg") val msg : String,
	@SerializedName("useremail") val useremail : String,
	@SerializedName("userpassword ") val userpassword : String
)

data class Msg (
	@SerializedName("msg") val msg : List<String>
)

data class ProjectList (
	@SerializedName("projects") val projects : List<Projects>
)

data class Projects (
	@SerializedName("id") val id : Int,
	@SerializedName("projectname") val projectname : String,
	@SerializedName("projectstatus") val projectstatus : Int,
	@SerializedName("projectdesc") val projectdesc : String,
	@SerializedName("startdate") val startdate : String,
	@SerializedName("endstart") val endstart : String
)

data class ProjectTaskList (
	@SerializedName("project task") val projectTasks : List<ProjectTask>
)

data class ProjectTask (
	@SerializedName("taskid") val taskid : Int,
	@SerializedName("projectid") val projectid : Int,
	@SerializedName("taskname") val taskname : String,
	@SerializedName("taskstatus") val taskstatus : Int,
	@SerializedName("taskdesc") val taskdesc : String,
	@SerializedName("startdate") val startdate : String,
	@SerializedName("endstart") val endstart : String
)

data class ProjectSubtaskList (
	@SerializedName("project sub task") val projectSubtasks : List<ProjectSubtask>
)

data class ProjectSubtask (
	@SerializedName("subtaskid") val subtaskid : Int,
	@SerializedName("taskid") val taskid : Int,
	@SerializedName("projectid") val projectid : Int,
	@SerializedName("subtaskname") val subtaskname : String,
	@SerializedName("subtaskstatus") val subtaskstatus : Int,
	@SerializedName("subtaskdesc") val subtaskdesc : String,
	@SerializedName("startdate") val startdate : String,
	@SerializedName("endstart") val endstart : String
)

data class EmployeeList (
	@SerializedName("employees") val employees : List<Employees>
)

data class Employees (
	@SerializedName("empid") val empid : Int,
	@SerializedName("empfirstname") val empfirstname : String,
	@SerializedName("emplastname") val emplastname : String,
	@SerializedName("empemail") val empemail : String,
	@SerializedName("empmobile") val empmobile : String,
	@SerializedName("empdesignation") val empdesignation : String,
	@SerializedName("dateofjoining") val dateofjoining : String
)

data class ViewTaskList (
	@SerializedName("view tasks") val viewTasks : List<ViewTasks>
)

data class ViewTasks (
@SerializedName("projectid") val projectid : Int,
@SerializedName("taskid") val taskid : Int,
@SerializedName("subtaskid") val subtaskid : Int
)

data class ViewsubtasksList (
	@SerializedName("viewsubtasks") val viewsubtasks : List<Viewsubtasks>
)

data class Viewsubtasks (
	@SerializedName("projectid") val projectid : Int,
	@SerializedName("taskid") val taskid : Int,
	@SerializedName("subtaskid") val subtaskid : Int
)

data class MembersList (
	@SerializedName("members") val members : List<Members>
)

data class Members (
	@SerializedName("assignid") val assignid : Int,
	@SerializedName("taskid") val taskid : Int,
	@SerializedName("subtaskid") val subtaskid : Int,
	@SerializedName("projectid") val projectid : Int,
	@SerializedName("userid") val userid : Int
)

data class User (
	@SerializedName("userid") val userid : Int,
	@SerializedName("userfirstname") val userfirstname : String,
	@SerializedName("userlastname") val userlastname : String,
	@SerializedName("useremail") val useremail : String,
	@SerializedName("usermobile") val usermobile : String
)

data class TaskPriority (
	@SerializedName("taskid") val taskid : Int,
	@SerializedName("projectid") val projectid : Int,
	@SerializedName("priority") val priority : Int
)

data class SubtaskPriority (
	@SerializedName("taskid") val taskid : Int,
	@SerializedName("subtaskid") val subtaskid : Int,
	@SerializedName("projectid") val projectid : Int,
	@SerializedName("priority") val priority : Int
)