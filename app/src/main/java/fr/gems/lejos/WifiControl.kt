package fr.gems.lejos

import android.util.Log
import java.io.*
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class WifiControl {
    var socket: Socket? = null
    val TAG = "WifiControl"

    private lateinit var listenThread: Future<*>
    private lateinit  var sendThread: Future<*>
    private val listenExecService: ExecutorService = Executors.newSingleThreadExecutor()
    private val sendExecService: ExecutorService = Executors.newSingleThreadExecutor()

    var `in`: BufferedReader? = null
    var out: BufferedWriter? = null
    fun open(ip: String, port : String) {
        listenThread = listenExecService.submit { Runnable() {
            try {
                socket = Socket(ip, port.toInt())
                `in` = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                out = BufferedWriter(OutputStreamWriter(socket!!.getOutputStream()))
                Log.d(TAG,java.lang.String.format("Connect on %s, port %d", ip, port))
                while (!socket!!.isClosed) {
                    var s: String? = ""
                    Log.d(TAG, "Waiting for msg...")
                    if (`in`!!.readLine().also { s = it } != null) Log.d(TAG, String.format("Msg receive : %s", s)) else socket!!.close()
                }
                Log.d(TAG, "Socket closed")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        }
    }

    fun close() {
        try {
            if (!socket!!.isClosed) socket!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun sendMsg(message: String?) {
        sendThread = sendExecService.submit {
            try {
                Log.d(TAG, String.format("Message send : %s", message))
                out!!.write(String.format("%s\n", message))
                out!!.flush()
            } catch (e: Exception) {
                Log.d(TAG, String.format("Impossible to send message : %s", e.message))
            }
        }
    }
}