package fr.gems.lejos.ui.connect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import fr.gems.lejos.R
import fr.gems.lejos.databinding.FragmentConnectBinding
import fr.gems.lejos.ui.history.HistoryViewModel

class ConnectFragment : Fragment() {

    private lateinit var connectViewModel: ConnectViewModel
    private lateinit var connectBinding: FragmentConnectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        connectViewModel =
            ViewModelProvider(this).get(ConnectViewModel::class.java)

        connectBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_connect,
            container,
            false
        )

        connectBinding.connectViewmodel = connectViewModel
        connectBinding.lifecycleOwner = this

        connectViewModel.initWifi()

        return connectBinding.root
    }

    override fun onPause() {
        super.onPause()
        connectViewModel.closeWifi()
    }
    override fun onDestroy() {
        super.onDestroy()
        connectViewModel.closeWifi()
    }

}