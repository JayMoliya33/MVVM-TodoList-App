package com.example.todoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todoapp.ui.fragment.MainActivity
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTodoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.showToast
import com.example.todoapp.viewmodel.TodoViewModel

class AddTodoFragment : Fragment(R.layout.fragment_add_todo) {

    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var addToDoViewModel: TodoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addToDoViewModel = (activity as MainActivity).viewModel

        binding.btnSaveTodo.setOnClickListener {
            insertTodo()
        }
    }

    private fun insertTodo() {
        val todoTitle = binding.evTodoTitle.text.toString()
        val todoDesc = binding.evTodoDescription.text.toString()
        val important = binding.cbImportant.isChecked

        if (todoTitle.isNotEmpty() && todoDesc.isNotEmpty()) {
            val todoItem = Todo(0, todoTitle, todoDesc, important)
            addToDoViewModel.insertTodo(todoItem)
            activity?.showToast("Successfully Added")
            findNavController().navigate(R.id.action_addTodoFragment_to_homeFragment)
        } else {
            activity?.showToast("Title and Description must be non-empty")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}