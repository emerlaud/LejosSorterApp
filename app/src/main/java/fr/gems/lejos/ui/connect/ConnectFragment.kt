package fr.gems.lejos.ui.connect

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.InputType
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

        val portField : EditText = view.findViewById(R.id.editPort)
        portField.doOnTextChanged{ text, _, _, _ ->
            connectViewModel.updatePort(text.toString().toInt())
        }


        val validateIP : Button = view.findViewById(R.id.validate_code_button)
        validateIP.setOnClickListener {
            connectViewModel.initWifi(connectViewModel.ip, connectViewModel.port.toString())
            Toast.makeText(activity,"Connexion établie", Toast.LENGTH_SHORT).show()
            showDialog()

        }


        return view
    }

    fun showDialog(){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.enter_pin)
        // Set up the input
        val input = EditText(activity)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint(R.string.code)

        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton(R.string.validate, DialogInterface.OnClickListener{ dialog, _ ->
            val tryPin = input.text.toString()
            if (tryPin != connectViewModel.pin ) {
                Toast.makeText(activity, "code invalide", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity, "code valide", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)

        alertDialog.show()
    }
}