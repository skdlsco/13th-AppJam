package com.quiet.thequiet.Activity;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.quiet.thequiet.APIRequest;
import com.quiet.thequiet.Fonts;
import com.quiet.thequiet.GpsInfo;
import com.quiet.thequiet.Place;
import com.quiet.thequiet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    static int REQUEST_PERMISSIONS = 100;

    private GoogleMap mMap;
    private GpsInfo gpsInfo;
    private Fonts fonts;
    private Toolbar toolBar;
    private TextView toolBarTitle, textUpdate, rankText, dbText, locationName, locationInfo;
    private ImageView star1, star2, star3, star4, star5, rankImage;
    private ArrayList<Place> places = new ArrayList<>();
    private ArrayList<Marker> markers = new ArrayList<>();
    private Gson gson = new Gson();

    Retrofit retrofit;
    APIRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fonts = new Fonts(this);

        init();
        setTexts();
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        retrofit = new Retrofit.Builder()
                .baseUrl(APIRequest.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiRequest = retrofit.create(APIRequest.class);
        gpsInfo = new GpsInfo(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_map);
        mapFragment.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("asdf", "request");
            Toast.makeText(gpsInfo, "현재 위치를 받아 올 수 없습니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSIONS);
        }
    }

    void init() {
        toolBar = (Toolbar) findViewById(R.id.main_toolBar);
        toolBarTitle = (TextView) findViewById(R.id.main_toolBar_title);
        textUpdate = (TextView) findViewById(R.id.main_text_update);
        rankText = (TextView) findViewById(R.id.main_rank_text);
        dbText = (TextView) findViewById(R.id.main_db_text);
        locationName = (TextView) findViewById(R.id.main_text_locationName);
//        locationInfo = (TextView) findViewById(R.id.main_text_locationInfo);

        star1 = (ImageView) findViewById(R.id.main_star_1);
        star2 = (ImageView) findViewById(R.id.main_star_2);
        star3 = (ImageView) findViewById(R.id.main_star_3);
        star4 = (ImageView) findViewById(R.id.main_star_4);
        star5 = (ImageView) findViewById(R.id.main_star_5);
        rankImage = (ImageView) findViewById(R.id.main_rank_image);
    }

    private void setTexts() {
        toolBarTitle.setTypeface(fonts.NanumBarunGothicBold);
        textUpdate.setTypeface(fonts.NanumBarunGothic);
        rankText.setTypeface(fonts.NanumBarunGothicBold);
        dbText.setTypeface(fonts.NanumBarunGothic);
        locationName.setTypeface(fonts.NanumBarunGothicBold);
//        locationInfo.setTypeface(fonts.NanumBarunGothic);
        toolBarTitle.setTypeface(fonts.NanumBarunGothic);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(10);
        setLocation();
        apiRequest.getLocations().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String json;
                try {
                    json = response.body().string();
                    Log.e("asdfasdf", json.toString());
                    places = gson.fromJson(json, new TypeToken<List<Place>>() {
                    }.getType());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Place place : places) {
                    markers.add(mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(place.getLatitude(), place.getLogitude()))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_main_location))
                            .title(place.getPlacename())));
                    int index = markers.size() - 1;
                    markers.get(index).setTag(place.getPlaceid());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (int i = 0; i < markers.size(); i++) {
                    if (markers.get(i).getTag() == marker.getTag()) {

                        places.get(i).getDecibel();
                        int db = places.get(i).getDecibel();
                        dbText.setText(places.get(i).getDecibel() + "db / 최근3시간동안평균");
                        textUpdate.setText("최근 업데이트 : " + places.get(i).getLastupdate());
                        locationName.setText(places.get(i).getPlacename());
                        switch (db / 10) {
                            case 0:
                            case 1:
                                rankText.setText("매우 종음");
                                rankImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_verygood));
                                star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                break;
                            case 2:
                            case 3:
                                rankText.setText("좋음");
                                rankImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_good));
                                star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_uncheck));
                                break;
                            case 4:
                            case 5:
                                rankText.setText("보통");
                                rankImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_normal));
                                star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_uncheck));
                                star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_uncheck));
                                break;
                            case 6:
                            case 7:
                                rankText.setText("나쁨");
                                rankImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_bad));
                                star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_check));
                                star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_uncheck));
                                star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_uncheck));
                                star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_uncheck));
                                break;
                            default:
                                rankText.setText("매우 나쁨");
                                rankImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_verybad));
                                star1.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_uncheck));
                                star2.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_uncheck));
                                star3.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_uncheck));
                                star4.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_uncheck));
                                star5.setImageDrawable(getResources().getDrawable(R.drawable.ic_main_star_uncheck));
                                break;
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        gpsInfo.stopUsingGPS();
        super.onDestroy();
    }

    void setLocation() {
        if (gpsInfo.isGetLocation()) {
            Log.e("asdf", "경도 " + gpsInfo.getLongitude());
            Log.e("asdf", "위도 " + gpsInfo.getLatitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsInfo.getLatitude(), gpsInfo.getLongitude()), 13.0f));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                setLocation();
            }
        }
    }
}
