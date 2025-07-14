package org.catsproject.project.data.database.configure

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.catsproject.project.data.database.models.CatsEntity
import org.catsproject.project.data.database.models.FavoriteUserEntity

object RealmDatabase {
    private val config = RealmConfiguration.Builder(
        schema = setOf(CatsEntity::class, FavoriteUserEntity::class)
    )
        .name("cats_database.realm")
        .schemaVersion(1)
        .deleteRealmIfMigrationNeeded()
        .build()

    private var _realm: Realm? = null

    val realm: Realm
        get() {
            return _realm ?: synchronized(this) {
                _realm ?: Realm.open(config).also { _realm = it }
            }
        }

}