package com.example.chekins_19

import com.google.android.gms.location.LocationResult

interface UbicacionListener {
    fun ubicacionResponse(locationResult: LocationResult)
}