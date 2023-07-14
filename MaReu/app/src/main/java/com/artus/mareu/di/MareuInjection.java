package com.artus.mareu.di;

import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.DataSource.MareuApiService;
import com.artus.mareu.utils.MareuViewModelFactory;

public class MareuInjection {

    public static MareuRepository createMareuRepository() {
        return new MareuRepository(new MareuApiService());
    }
}


