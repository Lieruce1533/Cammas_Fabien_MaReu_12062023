package com.artus.mareu.di;

import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.DataSource.MareuApiService;

public class RepositoryInjection {

    public static MareuRepository createMareuRepository(){
        return new MareuRepository(new MareuApiService());
    }
}
