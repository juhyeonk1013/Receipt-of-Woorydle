package com.flowerroad.receiptofwoorydle;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import android.support.v4.app.FragmentActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by jaeyeon on 2018-01-25.
 */

public class ReceiptTextInsertActivity extends FragmentActivity  {

    int mYear, mMonth, mDay, mHour, mMinute;
    EditText mDateDisplay;
    EditText mTimeDisplay;

    //private GoogleApiClient mGoogleApiClient;

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_text_insert);

        //장소 입력상자
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("ReceiptTextInsert", "Place: " + place.getName());
            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("ReceiptTextInsert", "An error occurred: " + status);
            }
        });

        //날짜 입력상자
        mDateDisplay = (EditText) findViewById(R.id.date_insert);
        mDateDisplay.setClickable(true);
        mDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReceiptTextInsertActivity.this, mDateSetListener, mYear, mMonth, mDay).show();
            }
        });

        //시간 입력상자
        mTimeDisplay = (EditText) findViewById(R.id.time_insert);
        mTimeDisplay.setClickable(true);
        mTimeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(ReceiptTextInsertActivity.this, mTimeSetListener, mHour, mMinute, false).show();
            }
        });

        //현재 날짜와 시간을 가져오기위한 Calendar 인스턴스 선언
        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);
        UpdateNow();//화면에 텍스트뷰에 업데이트 해줌.

        // 확인 버튼 리스너
        Button btn = (Button) findViewById(R.id.finish);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TextDetectionIntent = new Intent(ReceiptTextInsertActivity.this, ReceiptListActivity.class);
                startActivity(TextDetectionIntent);
            }
        });
    }

    //날짜 대화상자 리스너 부분
    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //사용자가 입력한 값을 가져온뒤
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            //텍스트뷰의 값을 업데이트함
            UpdateNow();
        }

    };

    //시간 대화상자 리스너 부분
    TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    //사용자가 입력한 값을 가져온뒤
                    mHour = hourOfDay;
                    mMinute = minute;

                    //텍스트뷰의 값을 업데이트함
                    UpdateNow();
                }

            };

    void UpdateNow(){
        mDateDisplay.setText(String.format(" %d-%d-%d", mYear, mMonth + 1, mDay));
        mTimeDisplay.setText(String.format(" %d:%d", mHour, mMinute));
    }

}
