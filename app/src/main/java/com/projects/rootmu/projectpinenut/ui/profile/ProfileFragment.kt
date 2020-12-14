package com.projects.rootmu.projectpinenut.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.ProfileFragmentBinding
import com.projects.rootmu.projectpinenut.utils.autoCleared
import com.projects.rootmu.projectpinenut.utils.onAnimationEnd
import com.projects.rootmu.projectpinenut.utils.onBackPressed
import com.projects.rootmu.projectpinenut.viewmodels.AccountsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : DialogFragment() {

    private var binding: ProfileFragmentBinding by autoCleared()
    private val viewModel: AccountsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner

        FirebaseAuth.getInstance().currentUser?.let {
            binding.user = it
        }

        retainInstance = true

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        onBackPressed {
            close()
        }
    }

    fun close(){
        activity?.onAnimationEnd(R.anim.slide_out_down, view) {
            activity?.supportFragmentManager?.beginTransaction().also { ft ->
                activity?.supportFragmentManager?.findFragmentByTag("profile")?.let {
                    ft?.remove(it)
                }
            }?.commit()
        }
    }

}