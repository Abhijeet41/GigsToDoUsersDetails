package com.abhi41.bookdetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.abhi41.bookdetails.databinding.ActivityMainBinding
import com.abhi41.bookdetails.local.UserEntity
import com.abhi41.bookdetails.presentation.MainViewModel
import com.abhi41.bookdetails.presentation.UsersList
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val books = resources.getStringArray(R.array.BooksItems)
        val languageAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_book_item, books)
        binding.txtBook.setAdapter(languageAdapter)

        nameFocusListener()
        numberFocusListener()
        clickListener()

    }

    private fun clickListener() {
        binding.btnSave.setOnClickListener {
            Log.d(TAG, "Selected book ${binding.txtBook.text}")
            binding.edtNameInput.helperText = validateName()
            binding.edtNumberInput.helperText = validateNumber()

            val validName = binding.edtNameInput.helperText == null
            val validPhone = binding.edtNumberInput.helperText == null

            if (validName && validPhone) {
                //save details
                val userName = binding.edtName.text.toString().trim()
                val phoneNumber = binding.edtNumber.text.toString().trim()
                val txtBook = binding.txtBook.text.toString().trim()

                lifecycleScope.launchWhenStarted {
                    mainViewModel.insertUser(
                        UserEntity(
                            userName = userName,
                            phoneNumber = phoneNumber,
                            bookName = txtBook
                        )
                    )
                    binding.edtName.text?.clear()
                    binding.edtNumber.text?.clear()
                }
                startActivity(Intent(this, UsersList::class.java))
            } else {
                Snackbar.make(binding.main, "Please enter valid Details", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnShowList.setOnClickListener {
            startActivity(Intent(this, UsersList::class.java))
        }
    }


    private fun nameFocusListener() {
        binding.edtName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.edtNameInput.helperText = validateName()
            }else{
                binding.edtNameInput.helperText = validateName()
            }
        }
    }

    private fun validateName(): String? {
        val edtName = binding.edtName.text.toString()
        if (edtName.isEmpty() && edtName.isNullOrEmpty()) {
            return "Name should not be blanked"
        }

        if (edtName.length > 15) {
            return "name should not be more than 15 char"
        }
        return null
    }

    private fun numberFocusListener() {
        binding.edtNumber.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.edtNumberInput.helperText = validateNumber()
            }
        }
    }

    private fun validateNumber(): String? {
        val edtNumber = binding.edtNumber.text.toString()
        if (edtNumber.isEmpty() && edtNumber.isNullOrBlank()) {
            return "Number should not be blanked"
        }
        if (edtNumber.length != 10) {
            return "Number should be 10 digit"
        }

        if (!edtNumber.matches(".*[1-9].*".toRegex())) {
            return "Number must be all digits"
        }
        return null
    }


}