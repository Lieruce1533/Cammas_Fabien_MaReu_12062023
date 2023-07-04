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
import com.artus.mareu.utils.MaReuViewModelFactory;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolbar.getRoot());
        //mViewModel = new ViewModelProvider(this).get(MeetingsViewModel.class);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, MeetingsFragment.class, null)
                    .commit();
        }
        AndroidThreeTen.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /**case R.id.filter_by_Date:
         //TODO dialog date picker -> mDate + call mViewModel.loadLiveListMeeting(mDate)
         return true;*/
        if (item.getItemId() == R.id.sub_item_1) {
            String room = item.getTitle().toString();
            Toast.makeText(this, room, Toast.LENGTH_SHORT).show();
            //TODO call mViewModel.loadLiveListMeeting(mDate)
            return true;
        } else if (item.getItemId() == R.id.sub_item_2) {
            return true;
        }else if (item.getItemId() == R.id.sub_item_3) {
            return true;
        }else if (item.getItemId() == R.id.sub_item_4) {
            return true;
        }else if (item.getItemId() == R.id.sub_item_5) {
            return true;
        }else if (item.getItemId() == R.id.sub_item_6) {
            return true;
        }else if (item.getItemId() == R.id.sub_item_7) {
            return true;
        }else if (item.getItemId() == R.id.sub_item_8) {
            return true;
        }else if (item.getItemId() == R.id.sub_item_9) {
            return true;
        }else if (item.getItemId() == R.id.sub_item_10) {
            return true;
        }else
        return super.onOptionsItemSelected(item);

    }

}