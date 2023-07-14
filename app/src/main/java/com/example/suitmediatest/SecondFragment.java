package com.example.suitmediatest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suitmediatest.databinding.FragmentSecondBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = view.findViewById(R.id.recyclerView);

        listingdata();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        return view;
    }

    private void listingdata() {
        ApiService apiService = com.example.suitmediatest.Retrofit.getRetrofit().create(ApiService.class);
        Call<Pojo> listingdata = apiService.getData();
        listingdata.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                if (response.isSuccessful()){
                    recycleadapter adapter = new recycleadapter(response.body().getData());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                Log.e("API Failure", "API Request Failed", t);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class recycleadapter extends RecyclerView.Adapter<recycleadapter.MyViewHolder>{
        List<Pojo.Datum> list;

        public recycleadapter(List<Pojo.Datum> list){
            this.list = list;
        }

        @NonNull
        @Override
        public recycleadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,null);
            recycleadapter.MyViewHolder viewHolder = new recycleadapter.MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull recycleadapter.MyViewHolder holder, int position) {
            holder.email.setText(list.get(position).getEmail());
            holder.first_name.setText(list.get(position).getFirstName());
            holder.last_name.setText(list.get(position).getLastName());
            holder.id.setText(String.valueOf(list.get(position).getId()));

            Picasso.with(getActivity().getApplicationContext())
                    .load(list.get(position).getAvatar())
                    .placeholder(R.drawable.ic_photo)
                    .fit()
                    .into(holder.avatar);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView id,first_name,last_name,email;
            ImageView avatar;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                email = itemView.findViewById(R.id.userEmail);
                id = itemView.findViewById(R.id.userid);
                first_name = itemView.findViewById(R.id.first_name);
                last_name = itemView.findViewById(R.id.last_name);
                avatar = itemView.findViewById(R.id.imageView2);

            }
        }
    }
}