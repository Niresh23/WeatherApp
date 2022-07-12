package com.nik.weather_app.di;

import com.nik.weather_app.repository.IRepository;
import com.nik.weather_app.repository.RepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public abstract class RepositoryModule {

    @Binds
    public abstract IRepository provideRepository(RepositoryImpl repositoryImpl);
}
