package ankuranurag2.blockemall.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import ankuranurag2.blockemall.R
import ankuranurag2.blockemall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        setUpView()
    }

    private fun setUpView(){
        with(binding){
            addFab.setOnClickListener {
                openActivityForContact()
            }
        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val contactUri = result?.data?.data?:return@registerForActivityResult
            val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER)
            val cursor = contentResolver.query(contactUri, projection,
                null, null, null)

            if (cursor != null && cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val name = cursor.getString(nameIndex)
                val number = cursor.getString(numberIndex)

                // do something with name and phone
            }
            cursor?.close()
        }
    }

    private fun openActivityForContact() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        resultLauncher.launch(intent)
    }
}