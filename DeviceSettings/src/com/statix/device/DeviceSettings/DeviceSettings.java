/*
* Copyright (C) 2016 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package com.statix.device.DeviceSettings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.util.Log;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.internal.util.aicp.FileUtils;

public class DeviceSettings extends PreferenceFragment {



    private static final String KEY_CATEGORY_REFRESH = "refresh";
    public static final String KEY_REFRESH_RATE = "refresh_rate";
    public static final String KEY_AUTO_REFRESH_RATE = "auto_refresh_rate";

    public static final String KEY_SETTINGS_PREFIX = "device_setting_";

    private static TwoStatePreference mRefreshRate;
    private static SwitchPreference mAutoRefreshRate;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        addPreferencesFromResource(R.xml.main);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        mAutoRefreshRate = (SwitchPreference) findPreference(KEY_AUTO_REFRESH_RATE);
        mAutoRefreshRate.setChecked(AutoRefreshRateSwitch.isCurrentlyEnabled(this.getContext()));
        mAutoRefreshRate.setOnPreferenceChangeListener(new AutoRefreshRateSwitch(getContext()));

        mRefreshRate = (TwoStatePreference) findPreference(KEY_REFRESH_RATE);
        mRefreshRate.setEnabled(!AutoRefreshRateSwitch.isCurrentlyEnabled(this.getContext()));
        mRefreshRate.setChecked(RefreshRateSwitch.isCurrentlyEnabled(this.getContext()));
        mRefreshRate.setOnPreferenceChangeListener(new RefreshRateSwitch(getContext()));

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference == mAutoRefreshRate) {
              mRefreshRate.setEnabled(!AutoRefreshRateSwitch.isCurrentlyEnabled(this.getContext()));
        }
        return super.onPreferenceTreeClick(preference);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

