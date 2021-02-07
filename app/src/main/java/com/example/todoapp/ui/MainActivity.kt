package com.example.todoapp.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.todoapp.R
import com.example.todoapp.database.TodoDatabase
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.viewmodel.TodoViewModel
import com.example.todoapp.viewmodel.TodoViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModels()

        setupActionBarWithNavController(findNavController(R.id.fragment))
    }

    private fun setUpViewModels(){
        val todoRepository = TodoRepository(TodoDatabase(this))
        val todoViewModelProviderFactory = TodoViewModelProviderFactory(todoRepository)
        viewModel = ViewModelProvider(this, todoViewModelProviderFactory).get(TodoViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph, null)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}