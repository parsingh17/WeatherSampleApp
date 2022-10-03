package com.parul.imdbapplication

import android.app.Application
import android.location.Address
import android.location.Geocoder
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.parul.imdbapplication.presentation.viewModel.CurrentLocationFragmentViewModel
import com.parul.imdbapplication.presentation.viewModel.CurrentLocationFragmentViewModel.Companion.NAV_BACK
import com.parul.imdbapplication.presentation.viewModel.CurrentLocationFragmentViewModel.Companion.NAV_NEXT
import io.mockk.verify
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.powermock.api.mockito.PowerMockito.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class CurrentLocationFragmentViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Test rule for making the RxJava to run synchronously in unit test
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }


    private val application = Mockito.mock(Application::class.java)

    private lateinit var viewModel: CurrentLocationFragmentViewModel

    private lateinit var mockFusedLocationProviderClient :FusedLocationProviderClient

    private lateinit var geocoding: Geocoder
    //lateinit var addressList: MutableList<Address>

    @Before
    fun setup() {
        viewModel = CurrentLocationFragmentViewModel(application)

        mockFusedLocationProviderClient = Mockito.mock(FusedLocationProviderClient::class.java)
        viewModel.fusedLocationProviderClient = mockFusedLocationProviderClient

        val myCurrentLatLngLiveData = LatLng(12.97, 77.66)
        viewModel.myCurrentLatLngLiveData = myCurrentLatLngLiveData
        geocoding = Mockito.mock(Geocoder::class.java)
        viewModel.geocoder = geocoding
    }


    @Test
    @kotlin.jvm.Throws(Exception::class)
    fun FromLocation_Location_Maybe_Locale() {
        doReturn(getMockAddresses()).`when`(geocoding)?.getFromLocation(12.97, 77.66, 1)
        //Log.d("Unit testing = addressList ", addressList?.get(0)?.countryName ?: "address is null")
        assert(true)
    }

    @Test
    fun `test updateCurrentAddress when geocoder and currentLatLng supplied`() {
        val mockGeocoder = mock(Geocoder::class.java)
        //val myData = Mockito.mock()
        //`when`(Geocoder(application, Locale.getDefault())).thenReturn(mockGeocoder)
        Mockito.`when`((mockGeocoder).getFromLocation(
            12.12,
            12.22, 1)).thenReturn(getMockAddresses())
        viewModel.updateCurrentAddress()
        verify { viewModel.address.value }

    }

    fun getMockAddresses(): ArrayList<Address> {
        var lists: ArrayList<Address> =  ArrayList<Address>()
        var address = Address(Locale.getDefault())
        address.setAddressLine(
            0,
            "XMF5+824, Suranjan Das Rd, Bhoomi Reddy Colony, New Tippasandra, Bengaluru, Karnataka 560075, India"
        )
        address.featureName = "New Tippasandra"
        lists.add(address)
        return lists

/*        var list = ArrayList<Address>().apply{
            add(Address(Locale("en-GB")))
        }
        list[0].setAddressLine(0,"XMF5+824, Suranjan Das Rd, Bhoomi Reddy Colony, New Tippasandra, Bengaluru, Karnataka 560075, India")
        return list*/
    }

    @Test
    fun `test onNextButtonClicked`() {
        viewModel.onNextButtonClicked()
        assertEquals(NAV_NEXT, viewModel.navigationCommand.value)
    }

    @Test
    fun `test onBackPressed`() {
        viewModel.onBackPressed()
        assertEquals(NAV_BACK, viewModel.navigationCommand.value)
    }
}