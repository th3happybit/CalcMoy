package oxxy.kero.roiaculte.team7.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import oxxy.kero.roiaculte.team7.data.database.LocalAuthentificator
import oxxy.kero.roiaculte.team7.data.database.LocalData
import oxxy.kero.roiaculte.team7.data.database.LocalModule
import oxxy.kero.roiaculte.team7.data.firebase.AuthentificationFirebase
import oxxy.kero.roiaculte.team7.data.firebase.FirebaseModule
import oxxy.kero.roiaculte.team7.data.firebase.RemoteDatabase
import oxxy.kero.roiaculte.team7.data.firebase.StorageHandler
import oxxy.kero.roiaculte.team7.data.repositories.AuthentificationRepositoryImpl
import oxxy.kero.roiaculte.team7.data.repositories.DataModelingRepositoryImpl
import oxxy.kero.roiaculte.team7.data.repositories.SearchRepositoryImpl
import oxxy.kero.roiaculte.team7.domain.repositories.AuthentificationRepository
import javax.inject.Singleton

@Module(includes=[FirebaseModule::class, LocalModule::class])
class DataModule {
    @Provides
    @Singleton
    fun provideRepo(auth : AuthentificationFirebase, local :LocalAuthentificator): AuthentificationRepository = AuthentificationRepositoryImpl(auth, local)
   @Provides
   @Singleton
   fun provideSearchRepo(database: FirebaseDatabase)=SearchRepositoryImpl(database)
   @Provides
   @Singleton
   fun provideDataRepo(auth:FirebaseAuth,storage:StorageHandler, remote:RemoteDatabase, local:LocalData )
           = DataModelingRepositoryImpl(auth , storage , remote , local)
}