package fr.gems.lejos.ui.connect

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import fr.gems.lejos.R

class ConnectFragment : Fragment() {


    private lateinit var connectViewModel: ConnectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        connectViewModel =
            ViewModelProvider(this).get(ConnectViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_connect, container, false)

        val ipField : EditText = view.findViewById(R.id.editIP)
        ipField.doOnTextChanged { text, _, _, _ ->
            connectViewModel.updateIp(text.toString())
        }
        connectViewModel.updateIp(ipField.text.toString())


        val validateIP : Button = view.findViewById(R.id.validate_code_button)
        validateIP.setOnClickListener {
            connectViewModel.initWifi(connectViewModel.ip, connectViewModel.port.toString())
            Toast.makeText(activity,"Connexion établie", Toast.LENGTH_SHORT).show()
        }

        return view
    }

}