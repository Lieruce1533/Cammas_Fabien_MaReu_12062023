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

import org.threeten.bp.LocalDate;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MeetingsViewModel mViewModel;
    private MaReuViewModelFactory mMaReuViewModelFactory;
    private MareuRepository mMareuRepository;
    private String mRoom;
    private LocalDate mDate;


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

    /**
     *
     * @return the instance of the factory
     */
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
            mRoom = item.getTitle().toString();
            mViewModel.loadLiveListMeeting(mRoom,mDate);
            return true;
        }else if (item.getItemId() == R.id.sub_item_2) {
            mRoom = item.getTitle().toString();
            mViewModel.loadLiveListMeeting(mRoom,mDate);
            return true;

        }else if (item.getItemId() == R.id.sub_item_3) {
            mRoom = item.getTitle().toString();
            mViewModel.loadLiveListMeeting(mRoom,mDate);
            return true;
        }else if (item.getItemId() == R.id.sub_item_4) {
            mRoom = item.getTitle().toString();
            mViewModel.loadLiveListMeeting(mRoom,mDate);
            return true;
        }else if (item.getItemId() == R.id.sub_item_5) {
            mRoom = item.getTitle().toString();
            mViewModel.loadLiveListMeeting(mRoom,mDate);
            return true;
        }else if (item.getItemId() == R.id.sub_item_6) {
            mRoom = item.getTitle().toString();
            mViewModel.loadLiveListMeeting(mRoom,mDate);
            return true;
        }else if (item.getItemId() == R.id.sub_item_7) {
            mRoom = item.getTitle().toString();
            mViewModel.loadLiveListMeeting(mRoom,mDate);
            return true;
        }else if (item.getItemId() == R.id.sub_item_8) {
            mRoom = item.getTitle().toString();
            mViewModel.loadLiveListMeeting(mRoom,mDate);
            return true;
        }else if (item.getItemId() == R.id.sub_item_9) {
            mRoom = item.getTitle().toString();
            mViewModel.loadLiveListMeeting(mRoom,mDate);
            return true;
        }else if (item.getItemId() == R.id.sub_item_10) {
            mRoom = item.getTitle().toString();
            mViewModel.loadLiveListMeeting(mRoom,mDate);
            return true;
        }else
        return super.onOptionsItemSelected(item);

    };

}