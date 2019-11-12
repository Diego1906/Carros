package livroandroid.com.br.fragments

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import livroandroid.com.br.R
import livroandroid.com.br.domain.Carro
import livroandroid.com.br.utils.PermissionUtils

class MapaFragment(val activity: Activity) : BaseFragment(), OnMapReadyCallback {

    // Objeto que controla o Google Maps
    private var map: GoogleMap? = null

    private val carro: Carro? by lazy {
        arguments?.getParcelable<Carro>("carroExtras")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mapa, container, false)

        // Inicia o mapa
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    // O método onMapReady(map) é chamado quando a inicialização do mapa estiver OK
    override fun onMapReady(map: GoogleMap) {

        this.map = map

        // Vamos mostrar a localização do usuário apenas para carros com lat/lng = 0
        if (carro?.latitude?.toDouble() == 0.0) {

            // Ativa o botão para mostrar minhalocalização
            val ok = PermissionUtils.validate(
                activity, 1,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )

            if (ok) {
                // Somente usa o GPS se a permissão estiver OK.
                map.isMyLocationEnabled = true
            }
        } else {

            var latitude: Double = 0.toDouble()
            var longitude: Double = 0.toDouble()

            carro?.let {
                it.latitude?.let {
                    latitude = it.toDouble()
                }

                it.longitude?.let {
                    longitude = it.toDouble()
                }
            }

            // Cria o objeto lat/lng com a coordenada da fábrica
            val location = LatLng(latitude, longitude)

            // Posiciona o mapa na coordenada da fábrica (zoom = 13)
            val update = CameraUpdateFactory.newLatLngZoom(location, 13f)
            map.moveCamera(update)

            // Marcador no local da fábrica
            map.addMarker(
                MarkerOptions()
                    .title(carro?.nome)
                    .snippet(carro?.descricao)
                    .position(location)
            )
        }
        // Tipo do mapa: normal, satélite, terreno ou híbrido
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_GRANTED) {
                // Permisssão OK, podemos usar o GPS.
                map?.isMyLocationEnabled = true
                return
            }
        }
    }
}

