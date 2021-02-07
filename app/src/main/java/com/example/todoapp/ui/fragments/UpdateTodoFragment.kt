package com.example.todoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentUpdateTodoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.showToast
import com.example.todoapp.ui.fragment.MainActivity
import com.example.todoapp.ui.fragments.UpdateTodoFragmentArgs
import com.example.todoapp.viewmodel.TodoViewModel

class UpdateTodoFragment : Fragment(R.layout.fragment_update_todo) {

    private val args by navArgs<UpdateTodoFragmentArgs>()

    private var _binding : FragmentUpdateTodoBinding? = null
    private val binding get()  = _binding!!

    private lateinit var updateViewModel: TodoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUpdateTodoBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateViewModel = (activity as MainActivity).viewModel

        binding.evUpdateTodoTitle.setText(args.currentTodo.title)
        binding.evUpdateTodoDescription.setText(args.currentTodo.description)
        binding.updateCbImportant.isChecked = args.currentTodo.isChecked

        binding.btnUpdateTodo.setOnClickListener {
            updateTodoItem()
        }
    }

    private fun updateTodoItem() {
        val updateTitle = binding.evUpdateTodoTitle.text.toString()
        val updateDesc = binding.evUpdateTodoDescription.text.toString()
        val updateCheckbox = binding.updateCbImportant.isChecked

        if (updateTitle.isNotEmpty() && updateDesc.isNotEmpty()) {
            val updatedTodoItem = Todo(args.currentTodo.id, updateTitle, updateDesc, updateCheckbox)
            updateViewModel.updateTodo(updatedTodoItem)
            activity?.showToast("Item Updated Successfully")
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_updateTodoFragment_to_homeFragment)
        } else {
            activity?.showToast("Please fill out all fields")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}