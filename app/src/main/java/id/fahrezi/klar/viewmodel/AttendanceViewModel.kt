package id.fahrezi.klar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.fahrezi.klar.service.ApiFactory
import id.fahrezi.klar.service.model.Request.AttendanceRequest
import id.fahrezi.klar.service.model.Response.AttendanceResponse
import id.fahrezi.klar.service.model.Response.StudentResponse
import id.fahrezi.klar.service.repository.AuthRepository
import id.fahrezi.klar.view.fragment.Attendance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AttendanceViewModel() : ViewModel() {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    val listStudent = MutableLiveData<AttendanceList>()
    val inputAttendance = MutableLiveData<Boolean>()

    data class AttendanceList(
        var student: List<StudentResponse>?,
        var attendance: List<AttendanceResponse>?
    )

    fun getListStudentAndAttendance(classId: String, scheduleId: String, accessToken: String) {
        var repo = AuthRepository(ApiFactory(accessToken).api)
        scope.launch {
            val student = repo.listStudent(classId)
            if (!student.isSuccessful) {
            }
            val attendance = repo.listAttendance(scheduleId)
            if (!attendance.isSuccessful) {
            }
            var attendanceList = AttendanceList(student.body(), attendance.body())
            listStudent.postValue(attendanceList)
        }
    }

    fun inputAttendance(accessToken: String, request: AttendanceRequest) {
        var repo = AuthRepository(ApiFactory(accessToken).api)
        scope.launch {
            val res = repo.inputAttendance(request)
            if (!res.isSuccessful) {
                inputAttendance.postValue(false)
                return@launch
            }
            inputAttendance.postValue(true)
        }
    }

}