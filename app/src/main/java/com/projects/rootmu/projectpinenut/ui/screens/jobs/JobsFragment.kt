package com.projects.rootmu.projectpinenut.ui.screens.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.JobsFragmentBinding
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.PopupType
import com.projects.rootmu.projectpinenut.ui.screens.dialog.NotifyingBaseFragment
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import java.io.Serializable
import javax.annotation.meta.Exhaustive


class JobsFragment : NotifyingBaseFragment<JobsFragment.DialogCategory>(), OnMapReadyCallback {

    sealed class DialogCategory : Serializable {
        object OnNetworkErrorRetryAvailable : DialogCategory()

        object OnNetworkError : DialogCategory()
    }

    interface Listener {
        fun openJob()
    }

    companion object {
        const val INDEX = 2
        private const val GOOGLEMAP_COMPASS = "GoogleMapCompass" // [4]
        private const val GOOGLEMAP_TOOLBAR = "GoogleMapToolbar" // [3]
        private const val GOOGLEMAP_ZOOMIN_BUTTON = "GoogleMapZoomInButton" // [2]child[0]
        private const val GOOGLEMAP_ZOOMOUT_BUTTON = "GoogleMapZoomOutButton" // [2]child[1]
        private const val GOOGLEMAP_MYLOCATION_BUTTON = "GoogleMapMyLocationButton" // [0]


    }

    private var adapter: JobsAdapter? = null
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var map: GoogleMap

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


        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        mainTabsViewModel.updateBottomNavigationCount(
//            INDEX,
//            if (adapter?.itemCount > 0) "${adapter.itemCount}" else null
//        )

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

    // OnMapReadyCallback

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        val dundee = LatLng(56.49526, -2.98452)
        //map.addMarker(MarkerOptions().position(dundee).title("Dundee"))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(dundee, 12F))
    }


}