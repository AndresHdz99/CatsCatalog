package org.catsproject.project.data.database.datasource

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.isValid
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.catsproject.project.core.toCatResultEntity
import org.catsproject.project.data.database.configure.RealmDatabase
import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.database.models.CatsEntity
import org.catsproject.project.data.database.models.FavoriteUserEntity

class RealmCatsFavorite(
    private val realm: Realm
) : FavoriteCatsDataSource {

    override suspend fun addFavoriteByUser(user: String, idCat: String) {
        val existingCat = realm.query<FavoriteUserEntity>(
            "user == $0 and idCat == $1", user, idCat
        ).first().find()



        realm.write {
            if (existingCat == null) {
                val favorite = FavoriteUserEntity().apply {
                    this.user = user
                    this.idCat = idCat
                }
                copyToRealm(favorite)
            }
        }
    }

    override suspend fun deleteFavoriteByUser(user: String, idCat: String) {
        val existingCat = realm.query<FavoriteUserEntity>(
            "user == $0 and idCat == $1", user, idCat
        ).first().find()

        if (existingCat != null) {
            realm.write {
                val liveCat = findLatest(existingCat)
                if (liveCat != null && liveCat.isValid()) {
                    delete(liveCat)
                }
            }
        }
    }

    override suspend fun getListMyCatsFavorite(user: String): Flow<List<CatResulEntity>> {
        return realm.query<FavoriteUserEntity>("user == $0", user)
            .asFlow()
            .map { resultsChange ->
                val favoriteIds = resultsChange.list.map { it.idCat }

                if (favoriteIds.isEmpty()) {
                    emptyList()
                } else {
                    realm.query<CatsEntity>("_id IN $0", favoriteIds)
                        .find()
                        .map { it.toCatResultEntity(favorite = true) }
                }
            }
    }



}
