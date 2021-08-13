package com.example.tpictest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.tpictest.fragments.HomeFragment;
import com.example.tpictest.fragments.MyPageFragment;
import com.example.tpictest.fragments.RankingFragment;
import com.example.tpictest.utils.CustomDialog;
import com.example.tpictest.utils.PreferenceSetting;

public class MainActivity extends AppCompatActivity {

    public enum PAGES {
        HOME, CATEGORY, EVALUATE, RANKING, MY_PAGE, MY_PAGE_SUB, SEARCH
    }

    public static PAGES CURRENT_PAGE;
    private static final String TAG = "TAG-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.USER_INFO));

        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fLyMain, homeFragment).commit();


        findViewById(R.id.iBtn_MHome).setOnClickListener(onClickListener);
        findViewById(R.id.iBtn_MRank).setOnClickListener(onClickListener);


        findViewById(R.id.iBtn_Mmypage).setOnClickListener(onClickListener);
    }

    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    View.OnClickListener onClickListener = v -> {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.iBtn_MHome:
                if (CURRENT_PAGE.equals(PAGES.HOME)) {
                    return;
                }
                HomeFragment homeFragment = new HomeFragment();
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fLyMain, homeFragment).commit();
                break;
            case R.id.iBtn_MRank:
                if (CURRENT_PAGE.equals(PAGES.RANKING)) {
                    return;
                }
                RankingFragment rankingFragment = new RankingFragment();
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fLyMain, rankingFragment).commit();
                break;
            case R.id.iBtn_Mmypage:
                if (CURRENT_PAGE.equals(PAGES.MY_PAGE)) {
                    return;
                }
                if (new PreferenceSetting(getBaseContext()).loadPreference(PreferenceSetting.PREFERENCE_KEY.LOGIN_TYPE).equals(LoginActivity.NO_LOGIN)) {
                    new CustomDialog(MainActivity.this, CustomDialog.DIALOG_CATEGORY.LOGIN, (response, data) -> {
                        if (response) {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }).show();
                } else {
                    MyPageFragment myPageFragment = new MyPageFragment();
//                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.replace(R.id.fLyMain, myPageFragment).commit();
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
        hideNavigationBar();
    }

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