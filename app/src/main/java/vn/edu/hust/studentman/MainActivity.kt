// app/src/main/java/vn/edu/hust/studentman/MainActivity.kt

package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  private lateinit var studentAdapter: StudentListAdapter
  private lateinit var listViewStudents: ListView
  private val students = mutableListOf<StudentModel>()

  private var selectedStudentPosition: Int = -1

  private lateinit var addStudentLauncher: ActivityResultLauncher<Intent>
  private lateinit var editStudentLauncher: ActivityResultLauncher<Intent>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Initialize the students list with initial data
    students.addAll(
      listOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
      )
    )

    // Initialize the adapter
    studentAdapter = StudentListAdapter(this, R.layout.list_item_student, students)

    // Set up the ListView
    listViewStudents = findViewById(R.id.list_view_students)
    listViewStudents.adapter = studentAdapter

    // Register the ListView for Context Menu
    registerForContextMenu(listViewStudents)

    // Initialize ActivityResultLaunchers
    initActivityResultLaunchers()
  }

  private fun initActivityResultLaunchers() {
    // Add Student Launcher
    addStudentLauncher = registerForActivityResult(
      ActivityResultContracts.StartActivityForResult()
    ) { result ->
      if (result.resultCode == Activity.RESULT_OK && result.data != null) {
        val data = result.data!!
        val student: StudentModel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          data.getParcelableExtra("student", StudentModel::class.java)
        } else {
          @Suppress("DEPRECATION")
          data.getParcelableExtra<StudentModel>("student")
        }
        student?.let {
          students.add(it)
          studentAdapter.notifyDataSetChanged()
        }
      }
    }

    // Edit Student Launcher
    editStudentLauncher = registerForActivityResult(
      ActivityResultContracts.StartActivityForResult()
    ) { result ->
      if (result.resultCode == Activity.RESULT_OK && result.data != null) {
        val data = result.data!!
        val student: StudentModel? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          data.getParcelableExtra("student", StudentModel::class.java)
        } else {
          @Suppress("DEPRECATION")
          data.getParcelableExtra<StudentModel>("student")
        }
        val position = data.getIntExtra("position", -1)
        if (position != -1 && student != null) {
          students[position] = student
          studentAdapter.notifyDataSetChanged()
        }
      }
    }
  }

  // Rest of your MainActivity code remains the same

  // Create Options Menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.options_menu, menu)
    return true
  }

  // Handle Options Menu item clicks
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_add_new -> {
        // Open AddStudentActivity
        val intent = Intent(this, AddStudentActivity::class.java)
        addStudentLauncher.launch(intent)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  // Create Context Menu for ListView items
  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu)

    // Get the position of the item that was long-pressed
    val info = menuInfo as AdapterView.AdapterContextMenuInfo
    selectedStudentPosition = info.position
  }

  // Handle Context Menu item clicks
  override fun onContextItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_edit -> {
        // Open EditStudentActivity
        val student = students[selectedStudentPosition]
        val intent = Intent(this, EditStudentActivity::class.java)
        intent.putExtra("student", student)
        intent.putExtra("position", selectedStudentPosition)
        editStudentLauncher.launch(intent)
        return true
      }
      R.id.menu_remove -> {
        // Remove student from the list
        students.removeAt(selectedStudentPosition)
        studentAdapter.notifyDataSetChanged()
        return true
      }
    }
    return super.onContextItemSelected(item)
  }
}
