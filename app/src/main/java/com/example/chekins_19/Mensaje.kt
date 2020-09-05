package com.example.chekins_19

import android.content.Context
import android.widget.Toast

class Mensaje {

    companion object{
        fun mensaje(context:Context, mensaje: Mensajes){
            var str = ""
            when(mensaje){
                Mensajes.RATIONALE->{
                    str = "Se requieren permisos para validar la ubicación."
                }
            }
        }

        fun mensaje_error(context: Context, error: Errores){
            var mensaje = ""
            when(error){

                Errores.NO_HAY_RED ->{
                    mensaje = "No hay conexión a internet."
                }

                Errores.HTTP_ERROR->{
                    mensaje = "Hubo un problema en la solicitud."
                }

                Errores.NO_HAY_APP_DE_FOURSQUARE->{
                    mensaje = "No tienes instalada la app de Foursquare."
                }

                Errores.NO_HAY_CONEXION_DE_FOURSQUARE->{
                    mensaje = "No se pudo completar la conexión con Foursquare."
                }

                Errores.ERROR_INTERCAMBIO_TOKEN->{
                    mensaje = "No se pudo completar el intercambio de token en Foursquare."
                }

                Errores.ERROR_AL_GUARDAR_TOKEN->{
                    mensaje = "No se pudo guardar el token."
                }

                Errores.PERMISO_NEGADO->{
                    mensaje = "No diste los permisos para obtener tu ubicación."
                }

                Errores.ERROR_QUERY->{
                    mensaje = "Hubo un problema en la solicitud a la API."
                }

            }

            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }

        fun mensaje_error(context: Context, error: String){
            Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
        }

    }
}