package org.catsproject.project.data.database.datasource

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.catsproject.project.core.copyFrom
import org.catsproject.project.core.toCatResultEntity
import org.catsproject.project.data.database.configure.RealmDatabase
import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.database.models.CatsEntity
import org.catsproject.project.data.database.models.FavoriteUserEntity

class RealmCatsDataSource(
    private val realm: Realm = RealmDatabase.realm
) : CatsDataSource {



    override fun getAllCats(): List<CatsEntity> {
        return realm.query<CatsEntity>().find()
    }

    override fun getCatsById(id: String, user: String): CatResulEntity? {
        return try {
            val catEntity = realm.query<CatsEntity>("_id == $0", id).first().find()
            val isFavorite = realm.query<FavoriteUserEntity>(
                "user == $0 AND idCat == $1", user, id
            ).first().find() != null


            catEntity?.toCatResultEntity(favorite = isFavorite)
        } catch (e: Exception) {
            null
        }
    }


    override suspend fun saveCat(cat: CatsEntity): Result<Unit> {
        return try {
            realm.write {
                val existingCat = query<CatsEntity>("_id == $0", cat._id).first().find()
                if (existingCat != null) {
                    existingCat.copyFrom(cat)
                } else {
                    val newCat = CatsEntity().apply {
                        _id = cat._id.ifEmpty { org.mongodb.kbson.ObjectId().toString() }
                        copyFrom(cat)
                    }
                    copyToRealm(newCat)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun saveCats(cats: List<CatsEntity>): Result<Unit> {
        return try {
            realm.write {
                cats.forEach { cat ->
                    val existingCat = query<CatsEntity>("_id == $0", cat._id).first().find()
                    if (existingCat != null) {
                        existingCat.copyFrom(cat)
                    } else {
                        val newCat = CatsEntity().apply {
                            _id = cat._id.ifEmpty { org.mongodb.kbson.ObjectId().toString() }
                            copyFrom(cat)
                        }
                        copyToRealm(newCat)
                    }
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    override suspend fun deleteCat(id: String): Result<Unit> {
        return try {
            realm.write {
                val catToDelete = query<CatsEntity>("_id == $0", id).first().find()
                catToDelete?.let { delete(it) }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAllCats(): Result<Unit> {
        return try {
            realm.write {
                val allCats = query<CatsEntity>().find()
                delete(allCats)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getListCatsByPage(page: Int): List<CatsEntity> {
        return realm.query<CatsEntity>("page == $0", page).find()
    }


    override suspend fun getListFavoriteByUser(user: String): Flow<List<CatResulEntity>> {
        return realm.query<FavoriteUserEntity>("user == $0", user)
            .asFlow()
            .map { results ->
                val favoriteIds = results.list.map { it.idCat }
                if (favoriteIds.isNotEmpty()) {
                    realm.query<CatsEntity>("_id IN $0", favoriteIds)
                        .find()
                        .map { cat -> cat.toCatResultEntity(true) }
                } else {
                    emptyList()
                }
            }
    }

    override suspend fun getListCatsCatalog(user: String, page: Int): Flow<List<CatResulEntity>> {
        return realm.query<CatsEntity>("page == $0", page)
            .asFlow()
            .map { catsResults ->
                val favorites = realm
                    .query<FavoriteUserEntity>("user == $0", user)
                    .find()
                    .map { it.idCat }
                    .toSet()


                catsResults.list.map { cat ->
                    cat.toCatResultEntity(favorite = favorites.contains(cat._id))

                }
            }
    }


    override suspend fun deleteCatsPage(page: Int) {
        realm.write {
            val favoriteIds = query<FavoriteUserEntity>()
                .find()
                .map { it.idCat }

            if (favoriteIds.isNotEmpty()) {
                val catsToDelete = query<CatsEntity>("page == $0 AND NOT _id IN $1", page, favoriteIds)
                    .find()
                delete(catsToDelete)
            } else {
                val catsToDelete = query<CatsEntity>("page == $0", page).find()
                delete(catsToDelete)
            }
        }
    }

}