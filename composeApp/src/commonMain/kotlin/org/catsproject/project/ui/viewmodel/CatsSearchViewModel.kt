package org.catsproject.project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.catsproject.project.core.BaseViewModel
import org.catsproject.project.core.SessionManager
import org.catsproject.project.core.ViewModelResetManager
import org.catsproject.project.data.database.domin.AddCatMyFavorite
import org.catsproject.project.data.database.domin.DeleteCatMyFavorite
import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepository
import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepositoryImpl
import org.catsproject.project.data.model.CatsImage
import org.catsproject.project.data.network.domain.GetCatsCatalogCatsApi
import org.catsproject.project.data.network.domain.GetCatsPageDataBase
import org.koin.mp.KoinPlatform


class CatsSearchViewModel(
    private val getCatsCatalogCatsApi: GetCatsCatalogCatsApi,
    private val getCatsPageDataBase: GetCatsPageDataBase,
    private val addCatMyFavorite: AddCatMyFavorite,
    private val deleteCatMyFavorite: DeleteCatMyFavorite,
    private val repository: CatsFavoriteBaseRepository,
    private val sessionManager: SessionManager
): BaseViewModel() {

    private val _catsList = MutableStateFlow<List<CatsImage>>(emptyList())
    val catsList = _catsList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _errorMore = MutableStateFlow<Boolean>(false)
    val errorMore = _errorMore.asStateFlow()


    private val _currentPage = MutableStateFlow<Int>(0)
    val currentPage: StateFlow<Int> = _currentPage


    private val _selectedCatId = MutableStateFlow<String?>(null)
    val selectedCatId: StateFlow<String?> = _selectedCatId

    fun selectCat(cat: CatsImage) {
        _selectedCatId.value = cat.id
    }


    private var canLoadMore = true
    private var isLoadingMore = false


    private val resetManager: ViewModelResetManager = KoinPlatform.getKoin().get()

    init {
        observeUserChanges(resetManager)
        loadInitialCats()

        viewModelScope.launch {
            repository.favoriteCatsUpdated.collect { updatedCatId ->
                toggleFavoriteById(updatedCatId)
            }
        }
    }


    private fun getCatsPageOnce() {
        viewModelScope.launch {
            val page = currentPage.first()
            val cats = getCatsPageDataBase(page, user = sessionManager.getUsername()).first()

            _catsList.update { oldList ->
                val newItems = cats.filterNot { newCat ->
                    oldList.any { existingCat -> existingCat.id == newCat.id }
                }
                oldList + newItems
            }
        }
    }

    private fun setCurrentPage(page: Int) {
        _currentPage.update {
            page
        }
    }


    fun stateFavorite(id: String, state:Boolean) {
        viewModelScope.launch {
            if (state){
                addCatMyFavorite(sessionManager.getUsername(),id)
            }else{
                deleteCatMyFavorite(sessionManager.getUsername(),id)
            }

        }
    }

    fun toggleFavoriteById(catId: String) {
        _catsList.update { currentList ->
            currentList.map { cat ->
                if (cat.id == catId) {
                    cat.copy(favorite = !cat.favorite)
                } else {
                    cat
                }
            }
        }
    }



    private fun loadInitialCats() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                getCatsCatalogCatsApi(page = 0).onSuccess {
                    getCatsPageOnce()
                    setCurrentPage(1)
                }.onFailure {
                    _errorMore.update {
                        true
                    }
                    getCatsPageOnce()
                    setCurrentPage(1)
                    println("Error: ${it.message}")
                }

            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
                canLoadMore = false
            } finally {
                //setCurrentPage(1)
                _isLoading.value = false
            }
        }
    }


    fun loadMoreCats() {


        if (isLoadingMore) return


        viewModelScope.launch {

            try {
                isLoadingMore = true
                _error.value = null
                _errorMore.value = false
                getCatsCatalogCatsApi(currentPage.value).onSuccess {
                    val nextPage = currentPage.value + 1
                    getCatsPageOnce()
                    setCurrentPage(nextPage)
                }.onFailure {
                    _errorMore.update {
                        true
                    }
                    getCatsPageOnce()
                    println("Error: ${it.message}")
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar más gatos"
            } finally {
                isLoadingMore = false
            }


        }
    }



    fun refresh() {
        viewModelScope.launch {
            try {
                _isRefreshing.value = true
                _error.value = null
                isLoadingMore = false

                getCatsCatalogCatsApi(0).onSuccess {
                    _catsList.value = emptyList()
                    setCurrentPage(0)
                    getCatsPageOnce()

                }.onFailure {
                    println("Error: ${it.message}")
                }

            } catch (e: Exception) {
                _error.value = e.message ?: "Error al actualizar"
            } finally {

                _isRefreshing.value = false
            }
        }
    }


    override fun resetState() {
       viewModelScope.launch {

           if (sessionManager.getUsername().isNotEmpty()){
               loadInitialCats()
           }else{
               _catsList.value = emptyList()
               _currentPage.value = 0
           }

       }

    }


}