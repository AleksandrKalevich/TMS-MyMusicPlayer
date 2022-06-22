package com.github.krottv.tmstemp.view

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.data.datastore.DataStoreCurrentContentTypeState
import com.github.krottv.tmstemp.data.permissions.PermissionState
import com.github.krottv.tmstemp.data.permissions.StoragePermissionChecker
import com.github.krottv.tmstemp.databinding.HostFragmentBinding
import com.github.krottv.tmstemp.domain.AlbumType
import com.github.krottv.tmstemp.domain.ContentType
import com.github.krottv.tmstemp.domain.purchase.PurchaseStateInteractor
import com.github.krottv.tmstemp.presentation.AlbumViewModel
import com.github.krottv.tmstemp.presentation.SongViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class HostFragment : Fragment() {

    private lateinit var fragment: HostFragmentBinding
    private val albumViewModel: AlbumViewModel by sharedViewModel()
    private val songViewModel: SongViewModel by sharedViewModel()
    private val purchaseStateInteractor: PurchaseStateInteractor by inject()
    private val dataStoreCurrentContentTypeState: DataStoreCurrentContentTypeState by inject()
    private val storagePermissionChecker: StoragePermissionChecker by inject { parametersOf(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = HostFragmentBinding.inflate(inflater)

        return fragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment.iTunes.setOnClickListener { changeCurrentSelection(ContentType.ITUNES) }
        fragment.library.setOnClickListener { changeCurrentSelection(ContentType.LIBRARY) }

        viewLifecycleOwner.lifecycleScope.launch {
            storagePermissionChecker.storagePermission.collectLatest { permissionState ->
                when (permissionState) {
                    PermissionState.HAS_PERMISSION -> fragment.myMusic.setOnClickListener { changeCurrentSelection(ContentType.MY_MUSIC) }
                    PermissionState.NO_PERMISSION -> storagePermissionChecker.startPermissionDialog()
                    PermissionState.REJECTED_ASK_AGAIN -> storagePermissionChecker.startPermissionDialog()
                    PermissionState.REJECTED_FOREVER -> fragment.myMusic.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launch {
            purchaseStateInteractor.isPremium.collect {
                if (it) fragment.purchase.visibility = View.GONE
                else fragment.purchase.apply {
                    visibility = View.VISIBLE

                    setOnClickListener {
                        parentFragmentManager
                            .beginTransaction()
                            .replace(R.id.host_container, PurchaseFragment())
                            .commit()
                    }
                }
            }
        }

        val currentContentType = dataStoreCurrentContentTypeState.getCurrentContentType()

        parentFragmentManager.findFragmentByTag("ALBUMS_FRAGMENT")?.arguments = Bundle()
        changeCurrentSelection(currentContentType)
    }

    private fun changeCurrentSelection(contentType: ContentType) {

        parentFragmentManager.findFragmentByTag("ALBUMS_FRAGMENT")?.requireArguments()
            ?.putSerializable("contentType", contentType)
        albumViewModel.loadData(contentType)
        dataStoreCurrentContentTypeState.setCurrentContentType(contentType)

        var albumId = 1L
        when (contentType) {
            ContentType.ITUNES -> setPrimary(fragment.iTunes)
            ContentType.LIBRARY -> setPrimary(fragment.library)
            ContentType.MY_MUSIC -> {
                setPrimary(fragment.myMusic)
                albumId = 3206900477280588601
            }
        }
        songViewModel.loadData(AlbumType(albumId, contentType))
    }

    private fun setPrimary(primary: TextView) {
        primary.apply {
            typeface = Typeface.DEFAULT_BOLD
            textSize = 18f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.selectedTextColor))
            isClickable = false
        }

        setSecondary(primary)
    }

    private fun setSecondary(exceptPrimary: TextView) {

        val textViewList = listOf(fragment.iTunes, fragment.library, fragment.myMusic)

        for (secondary in textViewList) {
            if (secondary != exceptPrimary) {
                secondary.apply {
                    typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
                    textSize = 16f
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
                    isClickable = true
                }
            }
        }
    }
}
