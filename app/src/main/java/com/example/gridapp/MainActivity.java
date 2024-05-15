package com.example.gridapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gridapp.api.ApiUtilities;
import com.example.gridapp.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private ArrayList<ImageModel> list;
    private GridLayoutManager manager;
    private  ImageAdapter adapter;
    private int page = 1;
    private ProgressDialog dialog;

    private int pageSize = 30;
    private boolean isLoading;
    private boolean isLastPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerview = findViewById(R.id.recyclerView);
        manager = new GridLayoutManager(this, 3);
        list = new ArrayList<>();
        adapter = new ImageAdapter(this, list);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        recyclerview.setHasFixedSize(true);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();
        getData();
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItem = manager.getChildCount();
                int totalItem = manager.getItemCount();
                int firstVisibleItemPos = manager.findFirstVisibleItemPosition();
                if (!isLoading && !isLastPage) {
                    if ((visibleItem + firstVisibleItemPos) >= totalItem && firstVisibleItemPos >= 0 && totalItem >= pageSize) {
                        page++;
                        getData();
                    }
                }
            }
        });
    }
    private void getData(){
        isLoading = true;
        ApiUtilities.getApiINterface().getImage(page, 30)
                .enqueue(new Callback<List<ImageModel>>() {
                    @Override
                    public void onResponse(Call<List<ImageModel>> call, Response<List<ImageModel>> response) {
                        if (response.body() != null) {
                            list.addAll(response.body());
                            adapter.notifyDataSetChanged();
                        }
                        isLoading = false;
                        dialog.dismiss();

                        if (list.size() >0) {
                            isLastPage = list.size() <pageSize;
                        } else isLastPage = true;
                    }

                    @Override
                    public void onFailure(Call<List<ImageModel>> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}