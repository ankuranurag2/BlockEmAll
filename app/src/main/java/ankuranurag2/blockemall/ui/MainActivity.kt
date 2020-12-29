package ankuranurag2.blockemall.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ankuranurag2.blockemall.R
import ankuranurag2.blockemall.data.local.ContactData
import ankuranurag2.blockemall.databinding.ActivityMainBinding
import ankuranurag2.blockemall.ui.adapter.ContactAdapter
import ankuranurag2.blockemall.util.SwipeToDeleteCallback
import ankuranurag2.blockemall.util.gone
import ankuranurag2.blockemall.util.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        setUpView()
        setUpObserver()
    }

    private fun setUpView() {
        with(binding) {
            addFab.setOnClickListener {
                openActivityForContact()
            }

            val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val contactToDelete = (viewHolder as ContactAdapter.ContactVH).itemContactBinding.contact
                    viewModel.deleteContact(contactToDelete!!)
                }
            }

            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(blockListRv)
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val contactUri = result?.data?.data ?: return@registerForActivityResult
                val projection = arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                )
                val cursor = contentResolver.query(
                    contactUri, projection,
                    null, null, null
                )

                if (cursor != null && cursor.moveToFirst()) {
                    val nameIndex =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val numberIndex =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val name = cursor.getString(nameIndex)
                    val number = cursor.getString(numberIndex)

                    viewModel.addContact(ContactData(number.replace(" ",""), name))
                }
                cursor?.close()
            }
        }

    private fun openActivityForContact() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        resultLauncher.launch(intent)
    }

    private fun setUpObserver() {
        val adapter = ContactAdapter()
        binding.blockListRv.adapter = adapter
        viewModel.contactLiveData.observe(this, Observer {
            with(binding) {
                if (it.isNullOrEmpty()) {
                    if (blockListRv.isVisible) {
                        blockListRv.gone()
                        emptyTv.visible()
                    }
                } else {
                    if (emptyTv.isVisible) {
                        emptyTv.gone()
                        blockListRv.visible()
                    }
                    adapter.submitList(it)
                }
            }
        })
    }
}