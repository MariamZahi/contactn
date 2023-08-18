import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var addButton: Button
    private lateinit var contactListView: ListView

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.nameEditText)
        numberEditText = findViewById(R.id.numberEditText)
        addButton = findViewById(R.id.addButton)
        contactListView = findViewById(R.id.contactListView)

        databaseHelper = DatabaseHelper(this)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val number = numberEditText.text.toString()
            val contact = Contact(0, name, number)
            databaseHelper.insertContact(contact)
            refreshContactList()
            clearInputFields()
        }

        refreshContactList()
    }

    private fun refreshContactList() {
        val contacts = databaseHelper.getAllContacts()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts)
        contactListView.adapter = adapter

        contactListView.setOnItemClickListener { _, _, position, _ ->
            val selectedContact = contacts[position]
            // Handle editing the contact details
        }

        contactListView.setOnItemLongClickListener { _, _, position, _ ->
            val selectedContact = contacts[position]
            databaseHelper.deleteContact(selectedContact.id)
            refreshContactList()
            true
        }
    }

    private fun clearInputFields() {
        nameEditText.text.clear()
        numberEditText.text.clear()
    }
}
