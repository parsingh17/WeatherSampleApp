package com.parul.imdbapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(Toast::class, ContextCompat::class)
class PermissionsUtilTest {

    @Mock
    private lateinit var mockFragment: Fragment

    @Mock
    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        PowerMockito.mockStatic(Toast::class.java)

        `when`(
            Toast.makeText(any(Context::class.java), anyString(), anyInt())
        ).thenReturn(Toast(mockContext))

        `when`(mockFragment.context).thenReturn(mockContext)
    }


    @Test
    fun `test checkLocationPermission does not have permission`() {
        PowerMockito.mockStatic(ContextCompat::class.java)

        `when`(ContextCompat.checkSelfPermission(any(Context::class.java), eq(Manifest.permission.ACCESS_COARSE_LOCATION))).thenReturn(PackageManager.PERMISSION_DENIED)

        assert(true)


    }
}