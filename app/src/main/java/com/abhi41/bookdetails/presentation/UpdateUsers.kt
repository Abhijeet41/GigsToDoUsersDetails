package com.abhi41.bookdetails.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.abhi41.bookdetails.R
import com.abhi41.bookdetails.databinding.ActivityUpdateBinding
import com.abhi41.bookdetails.local.UserEntity
import com.abhi41.bookdetails.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateUsers : AppCompatActivity() {
    var id: Int? = null
    lateinit var binding: ActivityUpdateBinding
    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update)
        nameFocusListener()
        numberFocusListener()
        getUserData()
        clickListeners()
        loadBooks()
    }

    private fun clickListeners() {
        binding.btnUpdate.setOnClickListener {
            binding.edtNameInput.helperText = validateName()
            binding.edtNumberInput.helperText = validateNumber()
            val validName = binding.edtNameInput.helperText == null
            val validPhone = binding.edtNumberInput.helperText == null

            if (validName && validPhone) {
                lifecycleScope.launchWhenStarted {
                    mainViewModel.updateUser(
                        UserEntity(
                            id = id!!,
                            userName = binding.edtName.text.toString().trim(),
                            phoneNumber = binding.edtNumber.text.toString().trim(),
                            bookName = binding.txtBook.text.toString().trim(),
                        )
                    )
                    startActivity(Intent(applicationContext, UsersList::class.java))
                    finish()
                }
            }


        }
    }

    private fun getUserData() {
        val user: UserEntity? = intent.getParcelableExtra(Constants.SEND_USER)
        user?.let {
            id = user.id
            binding.edtName.setText(user.userName).toString().trim()
            binding.edtNumber.setText(user.phoneNumber).toString().trim()
            binding.txtBook.setText(user.bookName).toString().trim()
        }
    }

    private fun loadBooks() {
        val books = resources.getStringArray(R.array.BooksItems)
        val languageAdapter = ArrayAdapter(applicationContext, R.layout.dropdown_book_item, books)
        binding.txtBook.setAdapter(languageAdapter)
    }

    private fun nameFocusListener() {
        binding.edtName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
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