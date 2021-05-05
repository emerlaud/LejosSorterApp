package fr.gems.lejos.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    //variables to stock the pin code for authentification
    var pin  : String = ""

    //define all the text fields
    private val _currentRed = MutableLiveData<Int>()
    val currentRed : LiveData<Int>
        get() = _currentRed

    private val _currentYellow = MutableLiveData<Int>()
    val currentYellow : LiveData<Int>
        get() = _currentYellow

    private val _currentBlue = MutableLiveData<Int>()
    val currentBlue : LiveData<Int>
        get() = _currentBlue

    private val _currentGreen = MutableLiveData<Int>()
    val currentGreen : LiveData<Int>
        get() = _currentGreen

    private val _quantityWaitRed = MutableLiveData<Int>()
    val quantityWaitRed : LiveData<Int>
        get() = _quantityWaitRed

    private val _quantityWaitYellow = MutableLiveData<Int>()
    val quantityWaitYellow : LiveData<Int>
        get() = _quantityWaitYellow

    private val _quantityWaitBlue = MutableLiveData<Int>()
    val quantityWaitBlue : LiveData<Int>
        get() = _quantityWaitBlue

    private val _quantityWaitGreen = MutableLiveData<Int>()
    val quantityWaitGreen : LiveData<Int>
        get() = _quantityWaitGreen

    //define the method to increase and decrease quantities wanted
    fun increaseRedWant() {
        _quantityWaitRed.value =(_quantityWaitRed.value)?.plus(1)
        Log.d("test button","add red, quantity = ${_quantityWaitRed.value}")

    }

    fun decreaseRedWant(){
        _quantityWaitRed.value = (_quantityWaitRed.value)?.minus(1)
        Log.d("test button", "rm red, quantity = ${_quantityWaitRed.value}")

    }

    fun increaseYellowWant() {
        _quantityWaitYellow.value =(_quantityWaitYellow.value)?.plus(1)
    }

    fun decreaseYellowWant(){
        _quantityWaitYellow.value = (_quantityWaitYellow.value)?.minus(1)
    }

    fun increaseBlueWant() {
        _quantityWaitBlue.value =(_quantityWaitBlue.value)?.plus(1)
    }

    fun decreaseBlueWant(){
        _quantityWaitBlue.value = (_quantityWaitBlue.value)?.minus(1)
    }

    fun increaseGreenWant() {
        _quantityWaitGreen.value =(_quantityWaitGreen.value)?.plus(1)
    }

    fun decreaseGreenWant(){
        _quantityWaitGreen.value = (_quantityWaitGreen.value)?.minus(1)
    }

    //define the method to check if the code is correct
    fun authentification(tryPin: String): Boolean {
        return tryPin == pin
    }

    init {
        pin = "0000"
        _quantityWaitRed.value = 0
        _quantityWaitYellow.value = 0
        _quantityWaitBlue.value = 0
        _quantityWaitGreen.value = 0

        _currentRed.value = 0
        _currentYellow.value = 0
        _currentBlue.value = 0
        _currentGreen.value = 0
    }

}