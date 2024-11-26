// app/src/main/java/vn/edu/hust/studentman/StudentListAdapter.kt
package vn.edu.hust.studentman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ArrayAdapter

class StudentListAdapter(
    context: Context,
    private val resource: Int,
    private val students: MutableList<StudentModel>
) : ArrayAdapter<StudentModel>(context, resource, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        val student = students[position]

        val textStudentName: TextView = view.findViewById(R.id.text_student_name)
        val textStudentId: TextView = view.findViewById(R.id.text_student_id)

        textStudentName.text = student.studentName
        textStudentId.text = student.studentId

        return view
    }
}
