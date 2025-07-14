package org.catsproject.project.data.database.configure

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.catsproject.project.core.di.isDesktop
import org.catsproject.project.data.database.models.CatsEntity
import org.catsproject.project.data.database.models.FavoriteUserEntity
import java.io.File


object RealmDatabase {
    val realm: Realm by lazy {
        val configBuilder = RealmConfiguration.Builder(
            schema = setOf(CatsEntity::class, FavoriteUserEntity::class)
        )
            .name("cats_database.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()


        if (isDesktop()) {
            val appDataDir = System.getProperty("user.home") + File.separator + ".yourAppName"
            File(appDataDir).mkdirs()
            configBuilder.directory(appDataDir)
        }

        Realm.open(configBuilder.build())
    }
}
