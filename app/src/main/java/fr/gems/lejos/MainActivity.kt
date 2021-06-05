package fr.gems.lejos

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val TAG = "MainActivity"
    private val mBluetoothAdapter: BluetoothAdapter? = null
    private var mDevice: BluetoothDevice? = null
    private val mSendBN: Button? = null

    private val MY_UUID = "00001101-0000-1000-8000-00805f9b34fb"
    private var mSocket: BluetoothSocket? = null
    private val mMessage = "Stop"
    private var sender: PrintStream? = null

    private fun findBrick() {
        val pairedDevices = mBluetoothAdapter
                ?.bondedDevices
        for (device in pairedDevices!!) {
            if (device.name == "EV3") mDevice = device
        }
    }

    private fun initBluetooth() {
        Log.d(TAG, "Checking Bluetooth...")
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "Device does not support Bluetooth")
            mSendBN!!.isClickable = false
        } else {
            Log.d(TAG, "Bluetooth supported")
        }
        if (!mBluetoothAdapter!!.isEnabled) {
            mSendBN!!.isClickable = false
            Log.d(TAG, "Bluetooth not enabled")
        } else {
            Log.d(TAG, "Bluetooth enabled")
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_history), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    @Throws(IOException::class)
    fun onSend(view: View?) {
        try {
            val os = mSocket!!.outputStream
            sender = PrintStream(os)
            Log.d("onSend", "Message = $mMessage")
            sender!!.println(mMessage)
            sender!!.flush()
            Log.d("onSend", "Message sent")
            mSocket!!.close()
            Log.d("onSend", "Socket closed")
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }
    @Throws(IOException::class)
    fun createSocket() {
        try {
            val uuid: UUID = UUID.fromString(MY_UUID)
            mSocket = mDevice!!.createRfcommSocketToServiceRecord(uuid)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d("createSocket", "Adapter")
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
        mSocket?.connect()
        val os: OutputStream = mSocket!!.outputStream
        sender = PrintStream(os)
        Log.d("createSocket", "End, " + "Socket: " + mSocket + " Sender: " + sender + " OutputStream: " + os + " mDevice: " + mDevice!!.name)
    }
}