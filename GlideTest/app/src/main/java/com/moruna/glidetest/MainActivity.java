package com.moruna.glidetest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.moruna.baselib.BaseActivity;
import com.moruna.glidetest.fragment.AsyncLoadPicturesFragment;
import com.moruna.glidetest.fragment.SyncLoadPicturesFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    public static final String FRAGMENT_TAG_SYNCLOADFRAGMENT = "fragment_tag_syncloadfragment";
    public static final String FRAGMENT_TAG_ASYNCLOADFRAGMENT = "fragment_tag_asyncloadfragment";

    private SyncLoadPicturesFragment syncLoadFragment;
    private AsyncLoadPicturesFragment asyncLoadFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            syncLoadFragment = (SyncLoadPicturesFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_TAG_SYNCLOADFRAGMENT);
            asyncLoadFragment = (AsyncLoadPicturesFragment) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_TAG_ASYNCLOADFRAGMENT);
        }

    }

    public void syncLoadPics(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideOtherFragment(fragmentTransaction, syncLoadFragment);
        if (syncLoadFragment == null) {
            syncLoadFragment = new SyncLoadPicturesFragment();
            fragmentTransaction.add(R.id.content_view, syncLoadFragment, FRAGMENT_TAG_SYNCLOADFRAGMENT);
        } else {
            fragmentTransaction.show(syncLoadFragment);
        }
        fragmentTransaction.commit();
    }

    public void asyncLoadPics(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideOtherFragment(fragmentTransaction, asyncLoadFragment);
        if (asyncLoadFragment == null) {
            asyncLoadFragment = new AsyncLoadPicturesFragment();
            fragmentTransaction.add(R.id.content_view, asyncLoadFragment, FRAGMENT_TAG_ASYNCLOADFRAGMENT);
        } else {
            fragmentTransaction.show(asyncLoadFragment);
        }
        fragmentTransaction.commit();
    }

    private void hideOtherFragment(FragmentTransaction transaction, Fragment excludeFragment) {
        if (syncLoadFragment != null && syncLoadFragment != excludeFragment) {
            transaction.hide(syncLoadFragment);
        }
        if (asyncLoadFragment != null && asyncLoadFragment != excludeFragment) {
            transaction.hide(asyncLoadFragment);
        }
    }
}
