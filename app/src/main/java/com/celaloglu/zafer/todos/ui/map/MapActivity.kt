package com.celaloglu.zafer.todos.ui.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.celaloglu.zafer.todos.R
import com.celaloglu.zafer.todos.base.BaseActivity
import com.celaloglu.zafer.todos.databinding.ActivityMapBinding
import com.celaloglu.zafer.todos.util.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : BaseActivity<ActivityMapBinding>(), OnMapReadyCallback {

    private var locations: ArrayList<LatLng>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locations = intent.getParcelableArrayListExtra(Constants.INTENT_EXTRA)
        if (locations == null)
            finish()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        supportActionBar?.title = getString(R.string.map_activity_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_map
    }

    override fun onMapReady(map: GoogleMap?) {
        locations?.let {
            for (location in locations!!) {
                map!!.addMarker(MarkerOptions().position(location))
            }
            map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(locations!![0], 15.0f))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {

        fun start(context: Context, locations: ArrayList<LatLng>?) {
            Intent(context, MapActivity::class.java)
                    .apply { putExtra(Constants.INTENT_EXTRA, locations) }
                    .also { context.startActivity(it) }
        }
    }
}