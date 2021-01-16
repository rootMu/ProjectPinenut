package com.projects.rootmu.projectpinenut.ui.screens.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.projects.rootmu.projectpinenut.databinding.FragmentDialogBannerBinding
import com.projects.rootmu.projectpinenut.ui.components.base.BaseDialogFragment
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.DialogResult
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getLongDelegate
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getValue
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.orThrow
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.setValue
import com.projects.rootmu.projectpinenut.ui.util.general.getMainHandler
import com.projects.rootmu.projectpinenut.ui.viewmodel.dialog.DialogInstanceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dialog_banner.*

@AndroidEntryPoint
class BannerDialogFragment : BaseDialogFragment() {

    companion object {
        const val DISMISS_DELAY_MS = 10 * 1000L // 10 seconds

        fun instantiate(id: Long): BannerDialogFragment {
            return BannerDialogFragment().apply {
                this.id = id
            }
        }
    }

    override var id by getLongDelegate("dialogId").orThrow()
        private set

    private var binding: FragmentDialogBannerBinding by autoCleared()

    private val dialogInstanceViewModel: DialogInstanceViewModel by viewModels()

    private val handler = getMainHandler()

    private val dismiss = Runnable {
        if (isResumed) {
            setResultAndDismiss()
        }
    }

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            setResultAndDismiss()
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            setResultAndDismiss()
            return true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDialogBannerBinding.inflate(inflater, container, false).let {
        binding = it
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogInstanceViewModel.getTypedData<DialogData.Banner>(id)?.let {
            setDialogData(it)
        }

        val gestureDetector = GestureDetector(view.context, gestureListener)

        banner.apply {
            setOnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
            }

            isClickable = true
        }


        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setGravity(Gravity.TOP)

            attributes = attributes.apply {
                flags = (flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()) or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            }
        }

    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(dismiss, DISMISS_DELAY_MS)
    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(dismiss)
    }

    private fun setResultAndDismiss() {
        dismiss()
        dialogInstanceViewModel.setResult(DialogResult.Primary())
    }

    private fun setDialogData(dialogData: DialogData.Banner) {
        binding.data = dialogData
    }

}