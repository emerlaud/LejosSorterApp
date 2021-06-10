package fr.gems.lejos.ui.connect

import android.util.Log
import androidx.lifecycle.ViewModel
import fr.gems.lejos.WifiControl
import layout.fr.gems.lejos.BTControl

class ConnectViewModel : ViewModel() {

    private var wifiControl : WifiControl = WifiControl()

    var ip = ""
    val TAG = "ConnectViewmodel"
    var port = 80
    var pin = "0000"

    private val msgTest = "{\"action\": \"connect\", \"KEYCODE\": \"$pin\"}"

    fun updateIp(value : String){
        ip = value
        Log.d(TAG, "ip = $ip")
    }

    fun initWifi(ip : String, port : String){
        wifiControl.open(ip, port)
        Log.d(TAG,"connexion open")
    }

    fun closeWifi(){
        wifiControl.close()
    }

    fun sendMsgWifi(){
        wifiControl.sendMsg(msgTest)
        Log.d(TAG, "message send")
    }

}