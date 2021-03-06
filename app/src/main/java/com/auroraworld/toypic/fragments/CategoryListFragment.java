package com.auroraworld.toypic.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.auroraworld.toypic.GoodsInfoActivity;
import com.auroraworld.toypic.MainActivity;
import com.auroraworld.toypic.R;
import com.auroraworld.toypic.db.DBRequestType;
import com.auroraworld.toypic.db.DatabaseRequest;
import com.auroraworld.toypic.list_code.ListAdapterCtgDetailIndex;
import com.auroraworld.toypic.list_code.ListAdptCategoryGoods;
import com.auroraworld.toypic.list_code.ListItemCategoryGoods;
import com.auroraworld.toypic.list_code.ListItemCtgDetailIndex;
import com.auroraworld.toypic.list_code.RecyclerDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String CATEGORY_CD = "param1";
    private static final String CATEGORY_NM = "param2";

    // TODO: Rename and change types of parameters
    private String categoryCd;
    private String categoryNm;

    private RecyclerView categoryGoodsView;
    private final String TAG = getClass().getSimpleName();

    public CategoryListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoryCd Parameter 1.
     * @param categoryNm Parameter 2.
     * @return A new instance of fragment CategoryListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryListFragment newInstance(String categoryCd, String categoryNm) {
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_CD, categoryCd);
        args.putString(CATEGORY_NM, categoryNm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryCd = getArguments().getString(CATEGORY_CD);
            categoryNm = getArguments().getString(CATEGORY_NM);
            if (categoryNm.isEmpty()) {
                categoryNm = getContext().getString(R.string.txt_category_noname);
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_category_list, container, false);

        view.findViewById(R.id.iBtnCListBack).setOnClickListener(onClickListener);
        ((TextView)view.findViewById(R.id.tVwCategoryListTitle)).setText(categoryNm);

        RecyclerView recyclerView = view.findViewById(R.id.rcyclVwCategoryIndex);
        setLayoutManager(recyclerView);
        setCategoryIndexList(recyclerView);

        categoryGoodsView = view.findViewById(R.id.rcyclVwCategoryGoods);
        setLayoutManager(categoryGoodsView);
//        setCategoryGoodsList(categoryGoodsView);

        return view;
    }

    private void setCategoryIndexList(RecyclerView recyclerView) {
        ArrayList<ListItemCtgDetailIndex> mList = new ArrayList<>();

        mList.add(addItem("0", "??????"));
        mList.add(addItem("1", "??????"));
        mList.add(addItem("2", "?????????"));
        mList.add(addItem("3", "??????"));
        mList.add(addItem("4", "??????"));

        recyclerView.setAdapter(new ListAdapterCtgDetailIndex(mList, indexId -> {
            //TODO: switching category goods
            setCategoryGoodsList(categoryGoodsView);
        }));
    }
    private ListItemCtgDetailIndex addItem(String... values) {
        ListItemCtgDetailIndex item = new ListItemCtgDetailIndex();
        item.setIndexId(values[0]);
        item.setIndexName(values[1]);
        return item;
    }

    private void setCategoryGoodsList(RecyclerView recyclerView) {
        JSONObject brandData = new JSONObject();
        try {
            brandData.put("brandCd", categoryCd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new DatabaseRequest(getContext(), result -> {
            JSONArray goodsList = new JSONArray();
            try {
                goodsList = new JSONArray(result[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (goodsList.length() < 1) {
                return;
            }
            ArrayList<ListItemCategoryGoods> mList = new ArrayList<>();
            for (int index=0; index < goodsList.length(); index++) {
                ListItemCategoryGoods item = new ListItemCategoryGoods();
                try {
                    item.setItem(goodsList.getJSONObject(index));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mList.add(item);
            }
            recyclerView.setAdapter(new ListAdptCategoryGoods(mList, goodsNo -> {
                JSONObject goodsData = new JSONObject();
                try {
                    goodsData.put("goodsNo", goodsNo);
                    new DatabaseRequest(getContext(), result1 -> {
                        if (result1[0].equals("null")) {
                            Toast.makeText(getContext(), "?????????????????? ????????????.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Intent intent = new Intent(getContext(), GoodsInfoActivity.class);
                        intent.putExtra("goodsInfo", result1[0]);
                        getContext().startActivity(intent);
                    }).execute(DBRequestType.GET_GOODS_INFO.name(), goodsData.toString());
                    Log.i(TAG, goodsData.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));
        }).execute(DBRequestType.GET_CATEGORY_GOODS.name(), brandData.toString());
    }

    @SuppressLint("NonConstantResourceId")
    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        switch (recyclerView.getId()) {
            case R.id.rcyclVwCategoryIndex:
                recyclerView.addItemDecoration(new RecyclerDecoration(5, 10));
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                break;
            case R.id.rcyclVwCategoryGoods:
                recyclerView.addItemDecoration(new RecyclerDecoration(0, 25));
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                break;
        }

        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.iBtnCListBack:
                MainActivity.CURRENT_PAGE = MainActivity.PAGES.valueOf(getParentFragmentManager().getBackStackEntryAt(0).getName());
                getParentFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    };
}