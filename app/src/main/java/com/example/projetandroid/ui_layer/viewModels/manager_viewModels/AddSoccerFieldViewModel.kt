package com.example.projetandroid.ui_layer.viewModels.manager_viewModels

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetandroid.BundledTextField
import com.example.projetandroid.Events
import com.example.projetandroid.R
import com.example.projetandroid.ShardPref
import com.example.projetandroid.SoccerFieldSubmissionFragments
import com.example.projetandroid.UiState
import com.example.projetandroid.data_layer.network.RetrofitInstance
import com.example.projetandroid.data_layer.network.api.AddressSearchApi
import com.example.projetandroid.data_layer.repository.AddressLookupRepository
import com.example.projetandroid.data_layer.repository.SoccerFieldRepository
import com.example.projetandroid.data_layer.repository.UserRepository
import com.example.projetandroid.model.AddressSearchJson
import com.example.projetandroid.model.SoccerField
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.time.LocalDate
import javax.inject.Inject


interface AddSoccerFieldViewModelProtocol {
    fun passSecondFragment()
    fun backFirstForm()
    fun submitSoccerField()
    fun clearFirstForm()
    fun lookupAddress(city: String)
    fun resetSharedFlow()
}


abstract class AddSoccerFieldViewModelBase : ViewModel(),
    AddSoccerFieldViewModelProtocol {

    // variables to handle transitions between screens ( 2 screen )
    private val _soccerFieldSubmissionFragment =
        mutableStateOf(SoccerFieldSubmissionFragments.FORM_FIRST_FRAGMENT)
    val soccerFieldSubmissionFragment: State<SoccerFieldSubmissionFragments> =
        _soccerFieldSubmissionFragment

    // variables to handle map interaction
    protected val _mapLangLat = mutableStateOf(LatLng(33.8869, 9.5375))
    val mapLangLat: State<LatLng> = _mapLangLat


    // variables to handle screen state (events, interactions)
    var isMapLoaded = false
    var isEnabled = mutableStateOf(true)
    protected val _sharedStateFlow = MutableStateFlow<UiState>(UiState.Idle)
    val sharedFlow: SharedFlow<UiState> = _sharedStateFlow


    // variables to handle search by address
    protected val _listOfAddresses = mutableStateListOf<AddressSearchJson>()
    val listOfAddresses: SnapshotStateList<AddressSearchJson> = _listOfAddresses


    // text fields
    val listViewModel = listOf(
        BundledTextField(
            mutableStateOf(""),
            label = "Soccer Field Name",
            supportErrorMessage = "value must be at least 4 characters",
            onErrorMessage = mutableStateOf(null),
            validator = "[a-zA-Z0-9 ]{4,}".toRegex(),
            iconId = R.drawable.stadium
        ),
        BundledTextField(
            mutableStateOf(""),
            label = "width, per metre",
            onErrorMessage = mutableStateOf(null),
            supportErrorMessage = "value must be a number eg 1.4",
            validator = "[0-9]+\\.?[0-9]*".toRegex(),
            iconId = R.drawable.width,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        ),
        BundledTextField(
            mutableStateOf(""),
            label = "height, per metre",
            iconId = R.drawable.height,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onErrorMessage = mutableStateOf(null),
            supportErrorMessage = "value must be a number eg 15.4",
            validator = "[0-9]+\\.?[0-9]*".toRegex(),
        ),
        BundledTextField(
            mutableStateOf(""),
            label = "price, par match with dt",
            iconId = R.drawable.baseline_price_check_24,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            onErrorMessage = mutableStateOf(null),
            supportErrorMessage = "value must be a number eg 40.4",
            validator = "[0-9]+\\.?[0-9]*".toRegex(),
        ),
    )


    fun changeLatLong(latlong: LatLng) {
        _mapLangLat.value = latlong
    }

    fun mapStatus(isMapLoadedParam: Boolean) {
        isMapLoaded = isMapLoadedParam
    }

    override fun passSecondFragment() {
        listViewModel.forEach { it.clearValidation() }
        listViewModel.filter { !it.validate() }.isEmpty().also {
            if (it)
                _soccerFieldSubmissionFragment.value =
                    SoccerFieldSubmissionFragments.MAP_SECOND_FRAGMENT
        }
    }

    override fun clearFirstForm() {
        listViewModel.map {
            it.value.value = ""
        }
    }

    override fun resetSharedFlow() {
        viewModelScope.launch {
            _sharedStateFlow.emit(UiState.Idle)
        }
    }

    override fun backFirstForm() {
        _soccerFieldSubmissionFragment.value = SoccerFieldSubmissionFragments.FORM_FIRST_FRAGMENT
    }
}

// this will be injected by hilt
@HiltViewModel
class AddSoccerFieldViewModelImp @Inject constructor(
    private val terrainRepository: SoccerFieldRepository,
    private val savedStateHandle: SavedStateHandle,
    private val shardPref: ShardPref
) : AddSoccerFieldViewModelBase() {

    override fun submitSoccerField() {
        terrainRepository.create(
            shardPref.getToken(),
            SoccerField(
                date = LocalDate.now().toString(),
                description = "",
                height = listViewModel[2].value.value.toDouble(),
                width = listViewModel[1].value.value.toDouble(),
                price = listViewModel[3].value.value.toDouble(),
                label = listViewModel[0].value.value,
                latitude = _mapLangLat.value.latitude.toString(),
                longitude = _mapLangLat.value.longitude.toString()
            )
        ).onEach {
            when (it) {
                is Events.SuccessEvent -> {
                    isEnabled.value = true
                    savedStateHandle["200"] = "OK"
                    _sharedStateFlow.value =
                        UiState.Success("Soccer filed Added With Success State")
                }

                is Events.ErrorEvent -> {
                    isEnabled.value = true
                    _sharedStateFlow.value = UiState.Error("Got Problem: ${it.error}")
                }

                is Events.LoadingEvent -> {
                    isEnabled.value = false
                    _sharedStateFlow.value = UiState.Loading()
                }
            }
        }.launchIn(viewModelScope)
    }


    override fun lookupAddress(city: String) {
    }
}

// this one for testing purpose
class AddSoccerFieldViewModelPreview : AddSoccerFieldViewModelBase() {


    //  initialize repository
    override fun submitSoccerField() {
        viewModelScope.launch {
            isEnabled.value = false
            _sharedStateFlow.emit(UiState.Loading("Adding soccer field"))
            delay(1000)
            _sharedStateFlow.emit(UiState.Success(message = "Soccer field added successfully"))
            // return input init state
            isEnabled.value = true
        }
    }

    override fun lookupAddress(city: String) {
    }

}


