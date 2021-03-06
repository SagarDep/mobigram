package net.mobindustry.mobigram.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import net.mobindustry.mobigram.R;
import net.mobindustry.mobigram.model.holder.MessagesFragmentHolder;
import net.mobindustry.mobigram.ui.fragments.ContactListFragment;
import net.mobindustry.mobigram.ui.fragments.FolderFragment;
import net.mobindustry.mobigram.ui.fragments.FoursquareListFragment;
import net.mobindustry.mobigram.ui.fragments.GalleryFragment;
import net.mobindustry.mobigram.ui.fragments.LocationFragment;
import net.mobindustry.mobigram.ui.fragments.SelectChatFragment;
import net.mobindustry.mobigram.ui.fragments.SelectedMapFragment;
import net.mobindustry.mobigram.ui.fragments.UserInfoFragment;
import net.mobindustry.mobigram.utils.Const;

public class TransparentActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transparent_activity);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        int choice = getIntent().getIntExtra("choice", 0);

        FrameLayout layout = (FrameLayout) findViewById(R.id.transparent_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (choice) {
            case Const.CONTACT_LIST_FRAGMENT: {
                String destination = getIntent().getStringExtra("destination");
                ContactListFragment contactListFragment = new ContactListFragment();
                fragmentTransaction.replace(R.id.transparent_content, contactListFragment);
                contactListFragment.setDestination(destination);
                break;
            }
            case Const.MAP_FRAGMENT: {
                LocationFragment locationFragment = new LocationFragment();
                fragmentTransaction.replace(R.id.transparent_content, locationFragment);
                break;
            }
            case Const.GALLERY_FRAGMENT: {
                GalleryFragment galleryFragment = new GalleryFragment();
                fragmentTransaction.replace(R.id.transparent_content, galleryFragment);
                break;
            }
            case Const.SELECTED_FOLDER_FRAGMENT: {
                FolderFragment folderFragment = new FolderFragment();
                fragmentTransaction.replace(R.id.transparent_content, folderFragment);
                break;
            }
            case Const.SELECTED_MAP_FRAGMENT: {
                SelectedMapFragment selectedMapFragment = new SelectedMapFragment();
                fragmentTransaction.replace(R.id.transparent_content, selectedMapFragment);
                double lat = getIntent().getDoubleExtra("lat", 0.0);
                double lng = getIntent().getDoubleExtra("lng", 0.0);
                selectedMapFragment.setUserLocation(lng, lat);
                break;
            }
            case Const.USER_INFO_FRAGMENT: {
                long chatId = getIntent().getLongExtra("chat_id", 0);
                String type = getIntent().getStringExtra("type");
                UserInfoFragment userInfoFragment = new UserInfoFragment();
                fragmentTransaction.replace(R.id.transparent_content, userInfoFragment);
                userInfoFragment.setInfo(chatId, type);
                break;
            }
            case Const.SELECT_CHAT: {
                SelectChatFragment selectChatFragment = new SelectChatFragment();
                fragmentTransaction.replace(R.id.transparent_content, selectChatFragment);
                break;
            }
        }
        fragmentTransaction.commit();
    }

    public void progressBarGone() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (getSupportFragmentManager().findFragmentById(R.id.transparent_content) instanceof FoursquareListFragment) {
            LocationFragment locationFragment = new LocationFragment();
            fragmentTransaction.replace(R.id.transparent_content, locationFragment);
            fragmentTransaction.commit();
        } else {
            if (getSupportFragmentManager().findFragmentById(R.id.transparent_content) instanceof FolderFragment) {
                GalleryFragment galleryFragment = new GalleryFragment();
                fragmentTransaction.replace(R.id.transparent_content, galleryFragment);
                fragmentTransaction.commit();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int choice = getIntent().getIntExtra("choice", 0);
        if (choice == 1312309183) {
            FolderFragment folderFragment = new FolderFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.transparent_content, folderFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        MessagesFragmentHolder.mapClosed();
        super.onDestroy();
    }
}
