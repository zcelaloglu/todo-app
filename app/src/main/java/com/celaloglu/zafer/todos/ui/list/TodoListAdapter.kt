package com.celaloglu.zafer.todos.ui.list

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.celaloglu.zafer.todos.database.ToDoItem
import com.celaloglu.zafer.todos.databinding.ItemTodoBinding
import java.lang.ref.WeakReference
import java.util.ArrayList
import kotlin.collections.LinkedHashMap

class TodoListAdapter(activity: TodoListActivity, todos: List<ToDoItem>) :
        RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>(), Filterable {

    private val activity = WeakReference(activity)

    private var mList: List<ToDoItem> = todos
    private var filter = TodoItemFilter()
    private var searchedTodos = todos as ArrayList<ToDoItem>?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(inflater, parent, false)
        return TodoListViewHolder(binding, activity)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        searchedTodos?.let {
            val item = it[position]
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return searchedTodos!!.size
    }

    class TodoListViewHolder(private val binding: ViewDataBinding,
                             private val activity: WeakReference<TodoListActivity>)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ToDoItem) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.view, activity.get())
            binding.executePendingBindings()
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    inner class TodoItemFilter : Filter() {

        override fun performFiltering(search: CharSequence): Filter.FilterResults {
            val results = Filter.FilterResults()

            if (TextUtils.isEmpty(search)) {
                results.count = -1
                return results
            }

            val searchString = search.toString().toLowerCase()
            val nlist = LinkedHashMap<String, ArrayList<ToDoItem>>(mList.size)
            val todos = ArrayList<ToDoItem>()

            for (i in mList.indices) {
                if (mList[i].title?.toLowerCase()?.startsWith(searchString.toLowerCase())!! ||
                        mList[i].description?.toLowerCase()?.startsWith(searchString.toLowerCase())!!) {
                    todos.add(mList[i])
                }
            }

            nlist["todos"] = todos
            results.values = nlist
            results.count = todos.size
            return results
        }

        override fun publishResults(charSequence: CharSequence, results: Filter.FilterResults) {
            if (results.count > 0) {
                searchedTodos = (results.values as LinkedHashMap<String, ArrayList<ToDoItem>>)["todos"]
                notifyDataSetChanged()
            }
        }
    }
}