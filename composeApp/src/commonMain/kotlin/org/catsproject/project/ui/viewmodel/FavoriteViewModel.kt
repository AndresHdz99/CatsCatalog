package org.catsproject.project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.catsproject.project.core.SessionManager
import org.catsproject.project.data.database.domin.AddCatMyFavorite
import org.catsproject.project.data.database.domin.DeleteCatMyFavorite
import org.catsproject.project.data.database.domin.GetListFavorites
import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepository
import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepositoryImpl
import org.catsproject.project.data.model.CatsImage

class FavoriteViewModel(
    private val getListFavorites: GetListFavorites,
    private val deleteCatMyFavorite: DeleteCatMyFavorite,
    private val repository: CatsFavoriteBaseRepository,
    private val sessionManager: SessionManager
):ViewModel() {

    private val _catsList = MutableStateFlow<List<CatsImage>>(emptyList())
    val catsList = _catsList.asStateFlow()

    init {
        getUserFavoriteCats()

        viewModelScope.launch {
            repository.favoriteCatsUpdated.collect { updatedCatId ->
                _catsList.update { currentList ->
                    currentList.filterNot { it.id == updatedCatId }
                }
            }
        }
    }

    fun getUserFavoriteCats(){
        viewModelScope.launch {
            getListFavorites(sessionManager.getUsername()).collect{ cats ->

                _catsList.update {
                    cats
                }
            }
        }
    }

    fun removeCatById(catId: String) {
        viewModelScope.launch {
            deleteCatMyFavorite(sessionManager.getUsername(), idCat = catId)
        }

    }


}