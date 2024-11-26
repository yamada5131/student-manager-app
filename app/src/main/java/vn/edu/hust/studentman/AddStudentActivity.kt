
package vn.edu.hust.studentman

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextId: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        editTextName = findViewById(R.id.edit_text_student_name)
        editTextId = findViewById(R.id.edit_text_student_id)
        buttonAdd = findViewById(R.id.button_save)
        buttonCancel = findViewById(R.id.button_cancel)

        buttonAdd.text = "Add"

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val id = editTextId.text.toString()
            if (name.isNotEmpty() && id.isNotEmpty()) {
                val student = StudentModel(name, id)
                val intent = intent
                intent.putExtra("student", student)
                setResult(Activity.RESULT_OK, intent)
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
