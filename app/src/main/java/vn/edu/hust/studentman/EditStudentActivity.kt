// app/src/main/java/vn/edu/hust/studentman/EditStudentActivity.kt

package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextId: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button

    private var studentPosition: Int = -1
    private lateinit var student: StudentModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        editTextName = findViewById(R.id.edit_text_student_name)
        editTextId = findViewById(R.id.edit_text_student_id)
        buttonSave = findViewById(R.id.button_save)
        buttonCancel = findViewById(R.id.button_cancel)

        buttonSave.text = "Save"

        // Retrieve student data
        student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("student", StudentModel::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<StudentModel>("student")!!
        }
        studentPosition = intent.getIntExtra("position", -1)

        if (studentPosition != -1) {
            editTextName.setText(student.studentName)
            editTextId.setText(student.studentId)
        }

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val id = editTextId.text.toString()
            if (name.isNotEmpty() && id.isNotEmpty()) {
                val updatedStudent = StudentModel(name, id)
                val resultIntent = Intent().apply {
                    putExtra("student", updatedStudent)
                    putExtra("position", studentPosition)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
