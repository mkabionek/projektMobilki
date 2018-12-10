package com.winiarza.asystent.asystentwiniarza;

import android.content.Intent;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class RemindersActivity extends AppCompatActivity {

    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        addBtn = findViewById(R.id.addReminderBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2018, 12, 11, 15, 00);
                Calendar endTime = Calendar.getInstance();
                endTime.set(2018, 12, 11, 23, 00);

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
                intent.putExtra(Events.TITLE, "Nalewka");
                intent.putExtra(Events.DESCRIPTION, "Zasypać cukrem");
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("allDay", true);

                startActivity(intent);
            }
        });
    }
}
