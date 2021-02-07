package com.example.todoapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.TodoAdapter
import com.example.todoapp.databinding.FragmentHomeBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.showToast
import com.example.todoapp.ui.fragment.MainActivity
import com.example.todoapp.viewmodel.TodoViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        homeViewModel = (activity as MainActivity).viewModel

        homeViewModel.getAllTodo().observe(viewLifecycleOwner, { todo ->
            todoAdapter.differ.submitList(todo)
            updateUI(todo)
        })

        setUpRecyclerView()

        binding.btnAddNote.setOnClickListener {
            findNavController()
                .navigate(R.id.action_homeFragment_to_addTodoFragment)
        }
    }

    private fun updateUI(todo: List<Todo>) {
        if (todo.isNotEmpty()) {
            binding.cardView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            binding.cardView.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        }
    }

    private fun setUpRecyclerView() {
        val dividerItemDecoration = DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        binding.recyclerView.apply {
            todoAdapter = TodoAdapter(homeViewModel)
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(dividerItemDecoration)
        }
    }

    // menu_item
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_remove -> {
                deleteCheckedTodo()
                true
            }
            R.id.menu_removeAll -> {
                deleteAllTodo()
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    private fun deleteAllTodo() {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Are you sure want to Delete All Items?")
            setPositiveButton("Yes") { _, _ ->
                homeViewModel.deleteAllTodo()
                activity?.showToast("Successfully Removed")
            }
            setNegativeButton("No") { _, _ -> }
            create()
            show()
        }
    }

    private fun deleteCheckedTodo() {
        val builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Are you sure want to Delete?")
            setPositiveButton("Yes") { _, _ ->
              //  homeViewModel.deleteTodo()
                todoAdapter.removeSelectedItem()
                activity?.showToast("Item Removed Successfully.")
            }
            setNegativeButton("No") { _, _ -> }
            create()
            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}