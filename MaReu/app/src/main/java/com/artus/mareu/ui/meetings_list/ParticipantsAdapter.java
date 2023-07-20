package com.artus.mareu.ui.meetings_list;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.artus.mareu.databinding.ItemParticipantBinding;
import com.artus.mareu.events.DeleteMeetingEvent;
import com.artus.mareu.events.DeleteParticipantEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ViewHolder>{

    private List<String> participants;

    public ParticipantsAdapter(List<String> participants) {
        this.participants = participants;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemParticipantBinding itemParticipantBinding = ItemParticipantBinding.inflate(layoutInflater,parent,false);
        return new ViewHolder(itemParticipantBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String participant = participants.get(position);
        holder.textView.setText(participant);

        // Set up the listener for the delete button
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DeleteParticipantEvent(participant));

            }
        });
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }


    public void onDelete(int position) {
        // Remove the participant from the list
        participants.remove(position);
        // Notify the adapter that the item has been removed
        notifyItemRemoved(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView deleteButton;

        public ViewHolder(ItemParticipantBinding binding) {
            super(binding.getRoot());

            textView = binding.itemParticipantTextView;
            deleteButton = binding.removeParticipant;
        }
    }
}
