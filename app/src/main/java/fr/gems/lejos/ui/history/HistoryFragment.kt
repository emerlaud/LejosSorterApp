package fr.gems.lejos.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.gems.lejos.R
import fr.gems.lejos.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private  lateinit var historyBinding: FragmentHistoryBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        historyViewModel =
                ViewModelProvider(this).get(HistoryViewModel::class.java)

        historyBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_history,
                container,
                false
        )

        historyBinding.historyViewmodel = historyViewModel
        historyBinding.lifecycleOwner = this

        return historyBinding.root
    }
}