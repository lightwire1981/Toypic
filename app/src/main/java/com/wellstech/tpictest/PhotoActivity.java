package com.wellstech.tpictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.wellstech.tpictest.list_code.ListAdptPhotoThumbnail;
import com.wellstech.tpictest.list_code.ListItemPhotoThumbnail;
import com.wellstech.tpictest.list_code.ListItemReviewImage;
import com.wellstech.tpictest.list_code.ListItemReviewToy;
import com.wellstech.tpictest.list_code.RecyclerDecoration;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {

    private ArrayList<ListItemReviewToy> reviewInfo;
    private ArrayList<ListItemReviewImage> photoInfo;
    private RecyclerView photoListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        getData();
        setWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setPhotoList();
    }

    private void setWidget() {
        photoListView = findViewById(R.id.rclVwPhotoThumbnail);
        setLayoutManager(photoListView);
        findViewById(R.id.iBtnPhotoExit).setOnClickListener(v -> finish());
    }

    private void getData() {
        reviewInfo = GoodsInfoActivity.reviewInfo;
        photoInfo = GoodsInfoActivity.imgList;
//        String reviewInfo = getIntent().getStringExtra("reviewInfo");
//        String photoInfo = getIntent().getStringExtra("photoInfo");
//        reviewInfo = (ArrayList<ListItemReviewToy>) getIntent().getSerializableExtra("reviewInfo");
//        photoInfo = (ArrayList<ListItemReviewImage>) getIntent().getSerializableExtra("photoInfo");

    }

    private void setPhotoList() {
        ArrayList<ListItemPhotoThumbnail> mList = new ArrayList<>();
        int count = photoInfo.size();
        int quotient = count/3;
        int remainder = count%3;
        int index = 0;
        if (quotient > 0) {
            for(int x=0; x < quotient; x++) {
                ListItemPhotoThumbnail item = new ListItemPhotoThumbnail();
                item.setItemCount(3);
                item.setPhoto1Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset1(photoInfo.get(index).getDataOffset());
                index++;
                item = new ListItemPhotoThumbnail();
                item.setPhoto2Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset2(photoInfo.get(index).getDataOffset());
                index++;
                item = new ListItemPhotoThumbnail();
                item.setPhoto3Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset3(photoInfo.get(index).getDataOffset());
                index++;
                mList.add(item);
            }
        }
        ListItemPhotoThumbnail item = new ListItemPhotoThumbnail();
        switch (remainder) {
            case 1:
                item.setItemCount(1);
                item.setPhoto1Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset1(photoInfo.get(index).getDataOffset());
                break;
            case 2:
                item.setItemCount(2);
                item.setPhoto1Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset1(photoInfo.get(index).getDataOffset());
                index++;
                item.setPhoto2Url(photoInfo.get(index).getPhotoUri());
                item.setDataOffset2(photoInfo.get(index).getDataOffset());
                break;
        }
        mList.add(item);
        photoListView.setAdapter(new ListAdptPhotoThumbnail(mList, dataOffset -> {
            ListItemReviewToy goodsReview = reviewInfo.get(dataOffset);
            // Call Review Activity
        }));
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}