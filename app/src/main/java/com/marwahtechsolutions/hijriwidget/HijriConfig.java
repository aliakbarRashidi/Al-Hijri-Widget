package com.marwahtechsolutions.hijriwidget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.KeyEvent;

public class HijriConfig extends PreferenceActivity implements OnSharedPreferenceChangeListener   {
	private static final String TAG = "HijriWidget";
	private boolean isAlarm = false;
	private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setResult(RESULT_CANCELED);

		Intent intent = getIntent();
		Bundle localBundle = intent.getExtras();
		if (localBundle != null) {
			appWidgetId = localBundle.getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);

		}
		addPreferencesFromResource(R.xml.settings);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent result = new Intent(this, HijriWidget.class);
			result.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
			result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
			result.putExtra("isAlarm", isAlarm);
			setResult(RESULT_OK, result);
			sendBroadcast(result);
			finish();
		}
		return (super.onKeyDown(keyCode, event));
	}

    @Override
    protected void onResume() {
        super.onResume();
        isAlarm = false;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("pMoonSight"))
            isAlarm = true;
    }
}