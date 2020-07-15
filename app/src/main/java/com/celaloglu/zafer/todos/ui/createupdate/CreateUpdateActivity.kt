package com.celaloglu.zafer.todos.ui.createupdate

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.celaloglu.zafer.todos.R
import com.celaloglu.zafer.todos.base.BaseActivity
import com.celaloglu.zafer.todos.database.TagItem
import com.celaloglu.zafer.todos.database.ToDoItem
import com.celaloglu.zafer.todos.databinding.ActivityCreateupdateBinding
import com.celaloglu.zafer.todos.receiver.AlarmReceiver
import com.celaloglu.zafer.todos.ui.createupdate.model.CreateUpdateModel
import com.celaloglu.zafer.todos.ui.model.*
import com.celaloglu.zafer.todos.util.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.concurrent.TimeUnit

class CreateUpdateActivity : BaseActivity<ActivityCreateupdateBinding>(), GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private val viewModel by viewModel<CreateUpdateViewModel>()

    private lateinit var googleApiClient: GoogleApiClient
    private var locationCallback: LocationCallback? = null

    private lateinit var datepickerdialog: DatePickerDialog

    private val uiModel = CreateUpdateModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLocationPermission()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.vm = viewModel
        observeActionState()

        uiModel.item = intent.getParcelableExtra(Constants.INTENT_EXTRA)
        if (uiModel.item != null) {
            setCurrentToDoItem(uiModel.item)
        } else {
            supportActionBar?.title = getString(R.string.add_new_todo_item_title)
            binding.dueDate.text = getString(R.string.add_new_todo_item_due_date)
        }
        setDateClickListener()
        if (uiModel.item != null) {
            getTags()
        }
        setTagTextChangeListener()
        setTagEditTextKeyListener()

        binding.titleTextinputlayout.requestFocus()
        showKeyboard(binding.titleTextinputlayout)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_createupdate
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save, menu)
        val save = menu?.findItem(R.id.save)
        save?.setOnMenuItemClickListener {
            viewModel.onSaveClick(CreateUpdateModel(uiModel.item, uiModel.dueDate,
                    uiModel.latitude, uiModel.longitude, uiModel.alarmTime,
                    binding.isCompleted.isChecked, binding.title.text.toString(),
                    binding.descriptionText.text.toString()))
            true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }

    override fun onResume() {
        super.onResume()
        connectGoogleApiClient()
    }

    override fun onStop() {
        googleApiClient.disconnect()
        removeLocationUpdates()
        viewModel.actionType.removeObservers(this)
        super.onStop()
    }

    override fun onConnected(p0: Bundle?) {
        requestLocationUpdates(getLocationRequest())
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (!(requestCode == Constants.LOCATION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            finish()
        }
    }

    private fun connectGoogleApiClient() {
        googleApiClient = GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build()
        googleApiClient.connect()
    }

    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(this, permission, Constants.LOCATION_REQUEST_CODE)
        }
    }

    private fun requestLocationUpdates(locationRequest: LocationRequest) {
        val hasAccessCoarsePermission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        if (hasAccessCoarsePermission == PackageManager.PERMISSION_GRANTED) {
            if (googleApiClient.isConnected) {
                try {
                    LocationServices.getFusedLocationProviderClient(this)
                            .requestLocationUpdates(locationRequest, getLocationCallback(), Looper.getMainLooper())
                } catch (e: SecurityException) {
                }
            }
        }
    }

    private fun removeLocationUpdates() {
        val hasAccessCoarsePermission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        if (hasAccessCoarsePermission == PackageManager.PERMISSION_GRANTED) {
            if (googleApiClient.isConnected) {
                try {
                    LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(locationCallback)
                    locationCallback = null
                } catch (e: SecurityException) {
                }
            }
        }
    }

    private fun getLocationCallback(): LocationCallback? {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                uiModel.latitude = result?.lastLocation?.latitude
                uiModel.longitude = result?.lastLocation?.longitude
                binding.location.text = LocationFormatter.format(result?.lastLocation?.latitude, result?.lastLocation?.longitude)
            }
        }
        return locationCallback
    }

    private fun getLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.create()
        return locationRequest.apply {
            interval = 300000
            fastestInterval = 150000
            maxWaitTime = 300000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }

    private fun setDateClickListener() {
        binding.dueDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            if (uiModel.item != null) {
                calendar.set(Calendar.DAY_OF_MONTH, DateFormatter.getDateElementFrom(uiModel.item?.dueDate, 0))
                calendar.set(Calendar.MONTH, DateFormatter.getDateElementFrom(uiModel.item?.dueDate, 1) - 1)
                calendar.set(Calendar.YEAR, DateFormatter.getDateElementFrom(uiModel.item?.dueDate, 2))
            }
            datepickerdialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                uiModel.dueDate = DateFormatter.formatForUI(dayOfMonth, month + 1, year)
                calendar.set(year, month, dayOfMonth)
                binding.dueDate.text = DateFormatter.format(uiModel.dueDate)
                uiModel.alarmTime = calendar.time.time + TimeUnit.SECONDS.toMillis(20)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datepickerdialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datepickerdialog.show()
        }
    }

    private fun setTagTextChangeListener() {
        binding.tagEditText.addTextChangedListener (object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val trimmed = s.toString()
                if (trimmed.length > 1 && trimmed.endsWith(" ")) {
                    val chip = Chip(this@CreateUpdateActivity)
                    chip.text = trimmed.substring(0, trimmed.length - 1)
                    chip.isCloseIconVisible = true

                    chip.setOnCloseIconClickListener {
                        binding.chipGroup.removeView(chip)
                    }
                    binding.chipGroup.addView(chip)
                    s?.clear()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun setTagEditTextKeyListener() {
        binding.tagEditText.setOnKeyListener { _, _, event ->
            if (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_DEL) {
                if (binding.tagEditText.length() == 0 && binding.chipGroup.childCount > 0) {
                    val chip = binding.chipGroup.getChildAt(binding.chipGroup.childCount - 1) as Chip
                    binding.chipGroup.removeView(chip)
                }
            }
            true
        }
    }

    private fun getTags() {
        viewModel.getTags(uiModel.item?.todoId)
    }

    private fun setCurrentToDoItem(item: ToDoItem?) {
        viewModel.setItemId(item?.todoId)
        binding.item = item
        supportActionBar?.title = item?.title
        binding.title.text = Editable.Factory.getInstance().newEditable(item?.title)
        binding.descriptionText.text = Editable.Factory.getInstance().newEditable(item?.description)
        binding.dueDate.text = DateFormatter.format(item?.dueDate)
        uiModel.dueDate = item?.dueDate
        uiModel.alarmTime = DateFormatter.getDateFrom(item?.dueDate).time + TimeUnit.SECONDS.toMillis(20)
        binding.isCompleted.isChecked = item?.completed ?: false
    }

    private fun observeActionState() {
        viewModel.actionType.removeObservers(this)
        viewModel.actionType.observe(this, Observer {
            hideKeyboard(binding.deleteButton)
            when (it) {
                is SetAlarm -> {
                    setAlarmForNotification(it.alarmTime, it.item)
                    finish()
                }
                is CancelAlarm -> {
                    cancelAlarm(it.item)
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                is Warning -> {
                    Snackbar.make(binding.deleteButton, resources.getString(it.message),
                            Snackbar.LENGTH_LONG).show()
                }
                is SaveTags -> {
                    saveTags()
                }
                is GetTags -> {
                    addChips(it.tags)
                }
                is FinishActivity -> {
                    finish()
                }
            }
        })
    }

    private fun addChips(tags: List<TagItem>) {
        for (tag in tags) {
            val chip = Chip(this@CreateUpdateActivity)
            chip.text = tag.title
            chip.isCloseIconVisible = true

            chip.setOnCloseIconClickListener {
                binding.chipGroup.removeView(chip)
            }
            binding.chipGroup.addView(chip)
        }
        setTagTextChangeListener()
        setTagEditTextKeyListener()
    }

    private fun saveTags() {
        for (index in 0 until binding.chipGroup.childCount) {
            val chip = binding.chipGroup.getChildAt(index) as Chip
            viewModel.insertTag(uiModel.item?.todoId, chip.text.toString())
        }
    }

    private fun cancelAlarm(item: ToDoItem?) {
        getAlarmManager().cancel(getIntent(item))
    }

    private fun getIntent(item: ToDoItem?): PendingIntent {
        val intent = Intent(Constants.TODO_ALARM_ACTION).apply {
            putExtra(Constants.INTENT_EXTRA_ID, item?.todoId)
            putExtra(Constants.INTENT_EXTRA_TITLE, item?.title)
            putExtra(Constants.INTENT_EXTRA_DESCRIPTION, item?.description)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            intent.setClass(this, AlarmReceiver::class.java)
        return PendingIntent.getBroadcast(this, item?.todoId ?: 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun setAlarmForNotification(alarmTime: Long, item: ToDoItem?) {
        getAlarmManager().setExact(AlarmManager.RTC_WAKEUP, alarmTime +
                TimeUnit.SECONDS.toMillis(10), getIntent(item))
    }

    private fun getAlarmManager(): AlarmManager {
        return getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    companion object {

        fun start(activity: Activity, item: ToDoItem?) {
            Intent(activity, CreateUpdateActivity::class.java)
                    .apply { putExtra(Constants.INTENT_EXTRA, item) }
                    .also { ActivityCompat.startActivityForResult(activity, it, 1, null) }
        }
    }
}