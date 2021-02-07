package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TodoItemBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.ui.fragments.HomeFragmentDirections
import com.example.todoapp.viewmodel.TodoViewModel

class TodoAdapter(private val viewModel: TodoViewModel) : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Todo>(){
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this,differCallback)

    inner class MyViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTodoItem = differ.currentList[position]

        holder.binding.apply {
            todoItemTitle.text = currentTodoItem.title
            todoItemDesc.text = currentTodoItem.description
            itemCheckbox.isChecked = currentTodoItem.isChecked

            itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    currentTodoItem.setSelected(true)
                }else{
                    currentTodoItem.setSelected(false)
                }
            }
            todoItemLayout.setOnClickListener {
                // pass current Data to Update Fragment
                val action = HomeFragmentDirections.actionHomeFragmentToUpdateTodoFragment(currentTodoItem)
                holder.itemView.findNavController().navigate(action)
            }

        }
    }

    override fun getItemCount() = differ.currentList.size

    fun removeSelectedItem(){
        for(todoItem in differ.currentList){
            if(todoItem.isSelected()){
               viewModel.deleteTodo(todoItem)
            }
        }
    }
}