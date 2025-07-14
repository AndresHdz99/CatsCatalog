package org.catsproject.project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.catsproject.project.core.SessionManager
import org.catsproject.project.data.database.domin.GetCatInformationDB
import org.catsproject.project.data.database.domin.AddCatMyFavorite
import org.catsproject.project.data.database.domin.DeleteCatMyFavorite
import org.catsproject.project.data.model.CatInformation

class CatInformationViewModel(
    private val getCatInformationDB: GetCatInformationDB,
    private val addCatMyFavorite: AddCatMyFavorite,
    private val deleteCatMyFavorite: DeleteCatMyFavorite,
    private val sessionManager: SessionManager
):ViewModel() {

    private val _description = MutableStateFlow<CatInformation?>(null)
    val description = _description.asStateFlow()


    fun getDescriptionCat(id:String){
        viewModelScope.launch {
            _description.value = getCatInformationDB(id,sessionManager.getUsername())
        }
    }


    fun stateFavorite(idCat: String,state:Boolean){
        viewModelScope.launch {
            _description.value = _description.value?.copy(
                favorite = state
            )
            if (state) addCatMyFavorite(sessionManager.getUsername(),idCat) else deleteCatMyFavorite(sessionManager.getUsername(), idCat)

        }
    }




}