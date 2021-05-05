package fr.gems.lejos.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.shape.ShapeAppearanceModel.builder
import fr.gems.lejos.R
import fr.gems.lejos.databinding.FragmentHomeBinding
import java.util.stream.DoubleStream.builder
import java.util.stream.IntStream.builder
import kotlin.math.log

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private  lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        homeBinding =DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        homeBinding.homeViewmodel = homeViewModel

        homeBinding.lifecycleOwner = this


        //allow navigation by the button breakdown
//        breakdown.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_breakdownFragment)
//        }

        showDialog()

        return homeBinding.root
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
            if (tryPin != homeViewModel.pin ) {
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