package fr.gems.lejos.ui.connect

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.gems.lejos.BT_Comm
import fr.gems.lejos.WifiControl
import layout.fr.gems.lejos.BTControl

class ConnectViewModel : ViewModel() {

    private  var BT_Comm : BTControl = BTControl()
    private var wifiControl : WifiControl = WifiControl()

    val TAG = "ConnectViewmodel"

    private val macTest = "00:16:53:81:87:DD"
    private val msgTest = "0"

    fun initBT(){
        BT_Comm.initBT()
    }

    //this function send the mac address to the robot
    fun sendMacBT(){
//        if (this.mac.value == null){
//            Log.d(TAG,"Error : mac adress is null")
//        } else{
            BT_Comm.connectToEV3(this.macTest)
            Log.d(TAG,"mac address send")
//        }

    }

    fun sendMsgBT(){
        BT_Comm.writeMessage(0)
        Log.d(TAG,"msg send")
    }


    fun initWifi(){
        wifiControl.ouvrir()
        Log.d(TAG,"connexion ouverte")
    }

    fun closeWifi(){
        wifiControl.fermer()
    }

    fun sendMsgWifi(){
        wifiControl.envoyerMessage(msgTest)
        Log.d(TAG, "message envoyé")
    }

}