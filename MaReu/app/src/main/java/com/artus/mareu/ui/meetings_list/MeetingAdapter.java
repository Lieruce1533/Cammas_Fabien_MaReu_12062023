package com.artus.mareu.ui.meetings_list;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artus.mareu.events.DeleteMeetingEvent;
import com.artus.mareu.model.Meeting;
import com.artus.mareu.databinding.ActivityMainBinding;
import com.artus.mareu.databinding.ItemviewMeetingBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder>{

    private final List<Meeting> mMeetingList;

    public MeetingAdapter(List<Meeting> meetingList) {
        mMeetingList = meetingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemviewMeetingBinding itemviewMeetingBinding = ItemviewMeetingBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemviewMeetingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = mMeetingList.get(position);
        holder.mMeetingTitle.setText(meeting.getTitle());
        holder.mMeetingDate.setText(meeting.getDateTimeMeeting().toLocalDate().toString());
        holder.mMeetingRoomName.setText(meeting.getLocation());
        holder.mMeetingTime.setText(meeting.getDateTimeMeeting().toLocalTime().toString());
        List<String> mParticipants = meeting.getParticipantsList();
        StringBuilder participantsBuilder = new StringBuilder();
        for (String item : mParticipants){
            participantsBuilder.append(item).append("\n");
        }
        holder.mMeetingParticipants.setText(participantsBuilder.toString());

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mMeetingTitle;
        ImageButton mDeleteButton;
        TextView mMeetingDate;
        TextView mMeetingRoomName;
        TextView mMeetingTime;
        TextView mMeetingParticipants;
        ItemviewMeetingBinding binding;


        public ViewHolder(@NonNull ItemviewMeetingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            mMeetingTitle = binding.itemListMeetingTitle;
            mDeleteButton = binding.itemListMeetingDeleteButton;
            mMeetingDate = binding.itemListMeetingDate;
            mMeetingRoomName = binding.itemListMeetingRoomName;
            mMeetingTime = binding.itemListMeetingHour;
            mMeetingParticipants = binding.itemListMeetingParticipants;

        }
    }
}
