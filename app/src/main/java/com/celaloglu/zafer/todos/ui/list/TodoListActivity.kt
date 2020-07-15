package com.celaloglu.zafer.todos.ui.list

import android.content.Intent
import androidx.lifecycle.Observer
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.celaloglu.zafer.todos.R
import com.celaloglu.zafer.todos.base.BaseActivity
import com.celaloglu.zafer.todos.database.ToDoItem
import com.celaloglu.zafer.todos.databinding.ActivityTodolistBinding
import com.celaloglu.zafer.todos.ui.model.Location
import com.celaloglu.zafer.todos.ui.model.Success
import com.celaloglu.zafer.todos.ui.createupdate.CreateUpdateActivity
import com.celaloglu.zafer.todos.ui.map.MapActivity
import com.google.android.gms.maps.model.LatLng
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoListActivity : BaseActivity<ActivityTodolistBinding>(),
        SearchView.OnQueryTextListener {

    private val viewModel by viewModel<TodoListViewModel>()

    private lateinit var adapter: TodoListAdapter
    private var query: String? = null
    private var locations: ArrayList<LatLng>? = null

    override fun onResume() {
        super.onResume()
        observeUiState()
        viewModel.getTodos()
        viewModel.getLocations()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_todolist
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchItem = menu?.findItem(R.id.search)
        val locationItem = menu?.findItem(R.id.location)
        locationItem?.setOnMenuItemClickListener {
            if (locations?.isNotEmpty()!!) {
                MapActivity.start(this, locations)
            }
            true
        }

        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setOnSearchClickListener { searchView.setQuery(query, false) }
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                searchItem.collapseActionView()
                searchView.setQuery("", false)
                query = ""
                viewModel.getTodos()
                viewModel.getLocations()
            }
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        this.query = query
        adapter.filter.filter(query)
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            observeUiState()
            viewModel.getLocations()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun observeUiState() {
        viewModel.viewState.observe(this, Observer {
            when (it) {
                is Success -> {
                    adapter = TodoListAdapter(this, it.data)
                    binding.todosRv.adapter = adapter
                    adapter.notifyDataSetChanged()
                    binding.fab.setOnClickListener {
                        CreateUpdateActivity.start(this, null)
                    }
                }
                is Location -> {
                    this.locations = it.data
                }
            }
        })
    }

    fun onTodoItemClick(item: ToDoItem) {
        CreateUpdateActivity.start(this@TodoListActivity, item)
    }
}
