package layout.fr.gems.lejos

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

class BTControl {

    private val SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB"
    private val TAG = "BTControl"

    var localAdapter: BluetoothAdapter? = null
    var socketEv3: BluetoothSocket? = null
    var socket_nxt2:BluetoothSocket? = null
    var success = false
    private var btPermission = false
    private var alertReplied = false

    fun reply() {
        alertReplied = true
    }

    fun setBtPermission(btPermission: Boolean) {
        this.btPermission = btPermission
    }

    fun initBT(): Boolean {
        localAdapter = BluetoothAdapter.getDefaultAdapter()

        Log.d(TAG,"local adapter is set")
        return localAdapter!!.isEnabled

    }


    //connect to both NXTs
    fun connectToEV3(macAdd: String): Boolean {

        //get the BluetoothDevice of the EV3
        val ev3 = localAdapter!!.getRemoteDevice(macAdd)
        Log.d(TAG,"get remote device by mac adress")
            socketEv3 = ev3.createRfcommSocketToServiceRecord(
                UUID
                    .fromString(this.SPP_UUID)
            )
            socketEv3?.connect()
            success = true

        return success
    }


    @Throws(InterruptedException::class)
    fun writeMessage(msg: Byte) {
        val connSock: BluetoothSocket? = socketEv3
        if (connSock != null) {
            try {
                val out = OutputStreamWriter(connSock.outputStream)
                out.write(msg.toInt())
                out.flush()
                Thread.sleep(1000)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            //Error
        }
    }

    // Note: Not needed for the current program
    fun readMessage(nxt: String): Int {
        val n: Int
        val connSock: BluetoothSocket? = when (nxt) {
            "nxt2" -> {
                socket_nxt2
            }
            "nxt1" -> {
                socketEv3
            }
            else -> {
                null
            }
        }
        return if (connSock != null) {
            try {
                val `in` = InputStreamReader(connSock.inputStream)
                n = `in`.read()
                n
            } catch (e: IOException) {
                e.printStackTrace()
                -1
            }
        } else {
            //Error
            -1
        }
    }

}