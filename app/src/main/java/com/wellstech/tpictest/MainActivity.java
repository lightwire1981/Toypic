package com.wellstech.tpictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wellstech.tpictest.db.DBRequestType;
import com.wellstech.tpictest.db.DatabaseRequest;
import com.wellstech.tpictest.fragments.CategoryFragment;
import com.wellstech.tpictest.fragments.EvaluateFragment;
import com.wellstech.tpictest.fragments.HomeFragment;
import com.wellstech.tpictest.fragments.MyPageFragment;
import com.wellstech.tpictest.fragments.RankingFragment;
import com.wellstech.tpictest.utils.CustomDialog;
import com.wellstech.tpictest.utils.PreferenceSetting;

public class MainActivity extends AppCompatActivity {

    public enum PAGES {
        HOME, CATEGORY, CATEGORY_LIST, EVALUATE, RANKING, MY_PAGE, MY_CHILD, MY_CHILD_EDIT, MY_PAGE_SUB, SEARCH, SETTING
    }

    public static PAGES CURRENT_PAGE;
    private static final String TAG = "TAG-MainActivity";
    public static String ALL_GOODS_INFO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO));

        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fLyMain, homeFragment).commit();


        findViewById(R.id.iBtnMainHome).setOnClickListener(onClickListener);
        findViewById(R.id.iBtnMainRank).setOnClickListener(onClickListener);
        findViewById(R.id.iBtnMainEvaluate).setOnClickListener(onClickListener);
        findViewById(R.id.iBtnMainCategory).setOnClickListener(onClickListener);
        findViewById(R.id.iBtnMainMypage).setOnClickListener(onClickListener);
    }

    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    View.OnClickListener onClickListener = v -> {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.iBtnMainHome:
                if (CURRENT_PAGE.equals(PAGES.HOME)) {
                    return;
                }
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.replace(R.id.fLyMain, homeFragment).commit();
                break;
            case R.id.iBtnMainCategory:
                Log.i(TAG, CURRENT_PAGE.name());
                if (CURRENT_PAGE.equals(PAGES.CATEGORY)) {
                    return;
                }
                CategoryFragment categoryFragment = new CategoryFragment();
                fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(R.id.fLyMain, categoryFragment).commit();
                break;
            case R.id.iBtnMainEvaluate:
                Log.i(TAG, CURRENT_PAGE.name());
                if (CURRENT_PAGE.equals(PAGES.EVALUATE)) {
                    return;
                }
                if (new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE).equals(LoginActivity.NO_LOGIN)) {
                    new CustomDialog(MainActivity.this, CustomDialog.DIALOG_CATEGORY.LOGIN, (response, data) -> {
                        if (response) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            hideNavigationBar();
                        }
                    }).show();
                } else {
                    EvaluateFragment evaluateFragment = new EvaluateFragment();
                    fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.add(R.id.fLyMain, evaluateFragment).commit();
                }
                break;
            case R.id.iBtnMainRank:
                Log.i(TAG, CURRENT_PAGE.name());
                if (CURRENT_PAGE.equals(PAGES.RANKING)) {
                    return;
                }
                RankingFragment rankingFragment = new RankingFragment();
                fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.add(R.id.fLyMain, rankingFragment).commit();
                break;
            case R.id.iBtnMainMypage:
                if (CURRENT_PAGE.equals(PAGES.MY_PAGE)) {
                    return;
                }
                if (new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE).equals(LoginActivity.NO_LOGIN)) {
                    new CustomDialog(MainActivity.this, CustomDialog.DIALOG_CATEGORY.LOGIN, (response, data) -> {
                        if (response) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            hideNavigationBar();
                        }
                    }).show();
                } else {
                    MyPageFragment myPageFragment = new MyPageFragment();
                    fragmentTransaction.addToBackStack(CURRENT_PAGE.name());
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.add(R.id.fLyMain, myPageFragment).commit();
//                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                }
                break;
            default:
                break;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        getGoodsInfo();
        hideNavigationBar();
    }

    @Override
    public void onBackPressed() {
        new CustomDialog(MainActivity.this, CustomDialog.DIALOG_CATEGORY.EXIT, (isAppFinish, data) -> {
            if (!isAppFinish) {
                hideNavigationBar();
            }
        }).show();
    }

    private void getGoodsInfo() {
        new DatabaseRequest(getBaseContext(), executeListener).execute(DBRequestType.GET_ALL_GOODS.name());
    }

    DatabaseRequest.ExecuteListener executeListener = result -> {
        ALL_GOODS_INFO = result[0];
//        try {
//            JSONArray goodsArray = new JSONArray(result[0]);
//            JSONObject goodsInfo = goodsArray.getJSONObject(0);
//            Log.i(TAG, goodsInfo.get("goodsNm").toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    };

    private void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
//                decorView.setSystemUiVisibility(uiOptions);
            } else {

            }
        });
    }
}