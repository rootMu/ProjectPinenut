package com.projects.rootmu.projectpinenut.ui.screens.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.JobsFragmentBinding
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.PopupType
import com.projects.rootmu.projectpinenut.ui.screens.dialog.NotifyingBaseFragment
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import kotlinx.android.synthetic.main.jobs_fragment.*
import java.io.Serializable
import javax.annotation.meta.Exhaustive

class JobsFragment : NotifyingBaseFragment<JobsFragment.DialogCategory>() {

    sealed class DialogCategory : Serializable {
        object OnNetworkErrorRetryAvailable : DialogCategory()

        object OnNetworkError : DialogCategory()
    }

    interface Listener {
        fun openJob()
    }

    companion object {
        const val INDEX = 2
    }

    private lateinit var adapter: JobsAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var binding: JobsFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = JobsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        items.adapter = JobsAdapter().apply {
            this.submitList(
                listOf(
                    JobsAdapter.Job(
                        "Moe The lawn",
                        "Moe Mrs Fellin's lawn, ensure it is no longer than 2cm."
                    ),
                    JobsAdapter.Job(
                        "Trim The Hedge",
                        "Trim Mrs Fellin's Hedge, ensure it maintains its current shape (Elephant)"
                    ),
                    JobsAdapter.Job(
                        "Clean the Pond",
                        "Clean Mr Dover's Pond, remove fallen leaves, empty and rinse filter"
                    )
                )
            )

            adapter = this
        }

        bottomNavigationViewModel.updateBottomNavigationCount(
            INDEX,
            if (adapter.itemCount > 0) "${adapter.itemCount}" else null
        )

        items.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false).also {
            layoutManager = it
        }
    }

    // Notifying

    override fun doPrimaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.OnNetworkErrorRetryAvailable -> {
                //DO Nothing
            }
            DialogCategory.OnNetworkError -> {
                //DO Nothing
            }
        }
    }

    override fun doSecondaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.OnNetworkErrorRetryAvailable -> {
                //DO Nothing
            }
            DialogCategory.OnNetworkError -> {
                //DO Nothing
            }
        }
    }

    override fun getDialogData(category: DialogCategory) = when (category) {
        is DialogCategory.OnNetworkErrorRetryAvailable -> DialogData.Basic.fromIds(
            resources,
            PopupType.ERROR,
            R.string.error_title,
            R.string.job_retrieval_failed_error_retry,
            R.string.retry,
            null
        )
        is DialogCategory.OnNetworkError -> DialogData.Basic.error(
            resources,
            descriptionId = R.string.job_retrieval_failed_error
        )
    }


}