package com.example.chekins_19

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

class Ubicacion(var activity: AppCompatActivity, ubicacionListener:UbicacionListener) {

    private val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION

    private val permisoCoarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION

    private val CODIGO_SOLICITUD_UBICACION = 100

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var locationRequest:LocationRequest? = null

    private var callback:LocationCallback? = null

    init {
        fusedLocationClient = FusedLocationProviderClient(activity.applicationContext)

        inicializarLocationRequest()

        callback = object: LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)

                ubicacionListener.ubicacionResponse(p0!!)
            }
        }
    }

    private fun inicializarLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest?.interval = 10000
        locationRequest?.fastestInterval = 5000
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun validarPermisosDeUbicacion(): Boolean{
        val hayUbicacionPrecisa = ActivityCompat.checkSelfPermission(activity.applicationContext, permisoFineLocation) == PackageManager.PERMISSION_GRANTED
        val hayUbicacionOrdinaria = ActivityCompat.checkSelfPermission(activity.applicationContext, permisoCoarseLocation) == PackageManager.PERMISSION_GRANTED

        return hayUbicacionOrdinaria && hayUbicacionOrdinaria
    }

    private fun pedirPermisos(){
        val deboProveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(activity, permisoFineLocation)

        if(deboProveerContexto){
            //Mandar un mensaje de explicación
            Mensaje.mensaje(activity.applicationContext,Mensajes.RATIONALE)
        }
            solicituDePermiso()
    }

    private fun solicituDePermiso(){
        ActivityCompat.requestPermissions(activity, arrayOf(permisoFineLocation,permisoCoarseLocation),CODIGO_SOLICITUD_UBICACION)
    }

    fun onRequestPermissionResult(requestCode:Int, permissions: Array<out String>, grantResult: IntArray){
        when(requestCode){
            CODIGO_SOLICITUD_UBICACION ->{
                if(grantResult.size > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                    // Tengo el permiso para obtener la ubicación
                    obtenerUbicacion()
                }else{
                    Mensaje.mensaje_error(activity.applicationContext, Errores.PERMISO_NEGADO)
                }
            }
        }
    }

    fun detenerActualizaciónDeUbicacion(){
        this.fusedLocationClient?.removeLocationUpdates(callback)
    }

    fun inicializarUbicación(){
        if(validarPermisosDeUbicacion()){
            obtenerUbicacion()
        }else{
            pedirPermisos()
        }

    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion(){
        validarPermisosDeUbicacion()
        fusedLocationClient?.requestLocationUpdates(locationRequest,callback,null)
    }
}