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

        return homeBinding.root
    }

}