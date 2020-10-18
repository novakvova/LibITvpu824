package com.example.libit.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.libit.R;
import com.example.libit.models.ProfileView;
import com.example.libit.models.State;

import java.util.List;

public class UserAdapter extends ArrayAdapter<ProfileView> {

    private LayoutInflater inflater;
    private int layout;
    private List<ProfileView> users;

    public UserAdapter(Context context, int resource, List<ProfileView> users) {
        super(context, resource, users);
        this.users = users;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        ImageView flagView = (ImageView) view.findViewById(R.id.flag);
        TextView nameView = (TextView) view.findViewById(R.id.name);
        TextView emailView = (TextView) view.findViewById(R.id.email);

        ProfileView user = users.get(position);

       // flagView.setImageResource(user.getFlagResource());
        nameView.setText(user.getName());
        emailView.setText(user.getEmail());

        return view;
    }
}
