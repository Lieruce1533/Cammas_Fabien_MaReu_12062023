package com.artus.mareu.ui.meetings_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.artus.mareu.R;
import com.artus.mareu.databinding.ActivityMainBinding;
import com.artus.mareu.databinding.ToolbarBinding;
import com.artus.mareu.di.RepositoryInjection;
import com.artus.mareu.repository.MareuRepository;
import com.artus.mareu.utils.MaReuViewModelFactory;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewModel mViewModel;
    private MaReuViewModelFactory mMaReuViewModelFactory;
    private MareuRepository mMareuRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolbar.getRoot());
        mMareuRepository = RepositoryInjection.createMareuRepository();
        mMaReuViewModelFactory = new MaReuViewModelFactory(mMareuRepository);
        mViewModel = new ViewModelProvider(this,mMaReuViewModelFactory).get(MeetingsViewModel.class);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, MeetingsFragment.class, null)
                    .commit();
        }
        AndroidThreeTen.init(this);
    }

    public MaReuViewModelFactory getMyFactory(){
        return mMaReuViewModelFactory;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filter_by_Date){
            //TODO dialog date picker -> mDate + call mViewModel.loadLiveListMeeting(mDate)
             return true;
        }else if (item.getItemId() == R.id.sub_item_1) {
            String room = item.getTitle().toString();
            Toast.makeText(this, room, Toast.LENGTH_SHORT).show();
            //TODO call mViewModel.loadLiveListMeeting(item.getTitle().toString())
            return true;
        }else if (item.getItemId() == R.id.sub_item_2) {
            //TODO call mViewModel.loadLiveListMeeting(item.getTitle().toString())
            return true;
        }else if (item.getItemId() == R.id.sub_item_3) {
            //TODO call mViewModel.loadLiveListMeeting(item.getTitle().toString())
            return true;
        }else if (item.getItemId() == R.id.sub_item_4) {
            //TODO call mViewModel.loadLiveListMeeting(item.getTitle().toString())
            return true;
        }else if (item.getItemId() == R.id.sub_item_5) {
            //TODO call mViewModel.loadLiveListMeeting(item.getTitle().toString())
            return true;
        }else if (item.getItemId() == R.id.sub_item_6) {
            //TODO call mViewModel.loadLiveListMeeting(item.getTitle().toString())
            return true;
        }else if (item.getItemId() == R.id.sub_item_7) {
            //TODO call mViewModel.loadLiveListMeeting(item.getTitle().toString())
            return true;
        }else if (item.getItemId() == R.id.sub_item_8) {
            //TODO call mViewModel.loadLiveListMeeting(item.getTitle().toString())
            return true;
        }else if (item.getItemId() == R.id.sub_item_9) {
            //TODO call mViewModel.loadLiveListMeeting(item.getTitle().toString())
            return true;
        }else if (item.getItemId() == R.id.sub_item_10) {
            //TODO call mViewModel.loadLiveListMeeting(item.getTitle().toString())
            return true;
        }else
        return super.onOptionsItemSelected(item);

    };

}