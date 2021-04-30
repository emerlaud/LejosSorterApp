package fr.gems.lejos.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    private val _historyRed = MutableLiveData<Int>()
    val historyRed : LiveData<Int>
        get() = _historyRed

    private val _historyYellow = MutableLiveData<Int>()
    val historyYellow : LiveData<Int>
        get() = _historyYellow

    private val _historyBlue = MutableLiveData<Int>()
    val historyBlue : LiveData<Int>
        get() = _historyBlue

    private val _historyGreen = MutableLiveData<Int>()
    val historyGreen : LiveData<Int>
        get() = _historyGreen

    init {
        _historyRed.value = 0
        _historyYellow.value = 0
        _historyBlue.value = 0
        _historyGreen.value = 0
    }
}