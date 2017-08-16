package com.driverhelper.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.driverhelper.R;
import com.driverhelper.config.Config;

import java.util.List;

import static com.driverhelper.config.Config.WriteSetting.CITY;
import static com.driverhelper.config.Config.WriteSetting.DISTANCE;
import static com.driverhelper.config.Config.WriteSetting.IMEI;
import static com.driverhelper.config.Config.WriteSetting.MODEL;
import static com.driverhelper.config.Config.WriteSetting.PROVINCE;
import static com.driverhelper.config.Config.WriteSetting.SN;
import static com.driverhelper.config.Config.WriteSetting.TCP_IP;
import static com.driverhelper.config.Config.WriteSetting.TCP_PORT;
import static com.driverhelper.config.Config.WriteSetting.TERMINALPHONENUMBER;
import static com.driverhelper.config.Config.WriteSetting.VEHICLE_COLOR;
import static com.driverhelper.config.Config.WriteSetting.VEHICLE_NUMBER;

public class SettingsActivity extends AppCompatPreferenceActivity {

    public Context context = this;

    private static Preference.OnPreferenceChangeListener sbindPreferenceSummaryToStrValueListener = new Preference.OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(Preference paramAnonymousPreference, Object paramAnonymousObject) {
            String str = paramAnonymousObject.toString();
            if ((paramAnonymousPreference instanceof ListPreference)) {
                ListPreference localListPreference = (ListPreference) paramAnonymousPreference;
                int i = localListPreference.findIndexOfValue(str);
                CharSequence localCharSequence = null;
                if (i >= 0)
                    localCharSequence = localListPreference.getEntries()[i];
                paramAnonymousPreference.setSummary(localCharSequence);
            }
            while (true) {
                if ((paramAnonymousPreference instanceof RingtonePreference)) {
                    if (TextUtils.isEmpty(str)) {
                        paramAnonymousPreference.setSummary("");
                    } else {
                        Ringtone localRingtone = RingtoneManager.getRingtone(paramAnonymousPreference.getContext(), Uri.parse(str));
                        if (localRingtone == null)
                            paramAnonymousPreference.setSummary(null);
                        else
                            paramAnonymousPreference.setSummary(localRingtone.getTitle(paramAnonymousPreference.getContext()));
                    }
                } else
                    paramAnonymousPreference.setSummary(str);

                return true;
            }
        }
    };

    private static void bindPreferenceSummaryToBoolValue(Preference paramPreference) {
        paramPreference.setOnPreferenceChangeListener(sbindPreferenceSummaryToStrValueListener);
        sbindPreferenceSummaryToStrValueListener.onPreferenceChange(paramPreference, Boolean.valueOf(PreferenceManager.getDefaultSharedPreferences(paramPreference.getContext()).getBoolean(paramPreference.getKey(), false)));
    }

    private static void bindPreferenceSummaryToStrValue(Preference paramPreference) {
        paramPreference.setOnPreferenceChangeListener(sbindPreferenceSummaryToStrValueListener);
        sbindPreferenceSummaryToStrValueListener.onPreferenceChange(paramPreference, PreferenceManager.getDefaultSharedPreferences(paramPreference.getContext()).getString(paramPreference.getKey(), ""));
    }

    private static boolean isXLargeTablet(Context paramContext) {
        return (0xF & paramContext.getResources().getConfiguration().screenLayout) >= 4;
    }

    private void setupActionBar() {
        ActionBar localActionBar = getSupportActionBar();
        if (localActionBar != null)
            localActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected boolean isValidFragment(String paramString) {
//        return ((DefaultPreferenceFragment.class.getName().equals(paramString)) || (Param1PreferenceFragment.class.getName().equals(paramString)));
        return (PreferenceFragment.class.getName().equals(paramString)) || (DefaultPreferenceFragment.class.getName().equals(paramString)) || (EDUParamPreferenceFragment.class.getName().equals(paramString)) || (Param1PreferenceFragment.class.getName().equals(paramString)) || (Param2PreferenceFragment.class.getName().equals(paramString)) || (Param3PreferenceFragment.class.getName().equals(paramString)) || (Param4PreferenceFragment.class.getName().equals(paramString)) || (Param5PreferenceFragment.class.getName().equals(paramString)) || (Param6PreferenceFragment.class.getName().equals(paramString)) || (Param7PreferenceFragment.class.getName().equals(paramString));
    }

    @Override
    @TargetApi(11)
    public void onBuildHeaders(List<Header> paramList) {
        loadHeadersFromResource(R.xml.pref_headers, paramList);
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setupActionBar();
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        if (paramMenuItem.getItemId() == 16908332) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(paramMenuItem);
    }


    @TargetApi(11)
    public static class DefaultPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.pref_default);
            setHasOptionsMenu(true);
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(PROVINCE));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(Config.WriteSetting.CITY));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("VENDER_ID"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(MODEL));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(SN));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(IMEI));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(VEHICLE_COLOR));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(VEHICLE_NUMBER));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(TCP_IP));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(TCP_PORT));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(TERMINALPHONENUMBER));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("KEMU"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("PERDRITYPE"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("CameraID"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("Car_ID"));
            SettingsActivity.bindPreferenceSummaryToBoolValue(findPreference("idsub0306ret"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
            if (paramMenuItem.getItemId() == 16908332) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(paramMenuItem);
        }
    }

    //
    @TargetApi(11)
    public static class EDUParamPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.pref_eduparam);
            setHasOptionsMenu(true);
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("PIC_INTV_min"));
            SettingsActivity.bindPreferenceSummaryToBoolValue(findPreference("UPLOAD_GBN"));
            SettingsActivity.bindPreferenceSummaryToBoolValue(findPreference("ADDMSG_YN"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("STOP_DELAY_TIME_min"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("STOP_GNSS_UPLOAD_INTV_sec"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("STOP_COACH_DELAY_TIME_min"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("USER_CHK_TIME_min"));
            SettingsActivity.bindPreferenceSummaryToBoolValue(findPreference("COACH_TRANS_YN"));
            SettingsActivity.bindPreferenceSummaryToBoolValue(findPreference("STU_TRANS_YN"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("DUP_MSG_REJECT_INTV_sec"));
        }

        public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
            if (paramMenuItem.getItemId() == 16908332) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(paramMenuItem);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
//if(sharedPreferences.){
//
//}
        }
    }

    @TargetApi(11)
    public static class Param1PreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.pref_param1);
            setHasOptionsMenu(true);
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0001"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0002"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0003"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0004"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0005"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0006"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0007"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
            if (paramMenuItem.getItemId() == 16908332) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(paramMenuItem);
        }
    }

    @TargetApi(11)
    public static class Param2PreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.pref_param2);
            setHasOptionsMenu(true);
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0010"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0011"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0012"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(TCP_IP));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0014"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0015"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0016"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0017"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(TCP_PORT));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0019"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
            if (paramMenuItem.getItemId() == 16908332) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(paramMenuItem);
        }
    }

    @TargetApi(11)
    public static class Param3PreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.pref_param3);
            setHasOptionsMenu(true);
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0020"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0021"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0022"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0027"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0028"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0029"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param002c"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param002d"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param002e"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param002f"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0030"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
            if (paramMenuItem.getItemId() == 16908332) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(paramMenuItem);
        }
    }

    @TargetApi(11)
    public static class Param4PreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.pref_param4);
            setHasOptionsMenu(true);
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0040"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0041"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0042"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0043"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0044"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0045"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0046"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0047"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0048"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0049"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
            if (paramMenuItem.getItemId() == 16908332) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(paramMenuItem);
        }
    }

    @TargetApi(11)
    public static class Param5PreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.pref_param5);
            setHasOptionsMenu(true);
            SettingsActivity.bindPreferenceSummaryToBoolValue(findPreference("param0050"));
            SettingsActivity.bindPreferenceSummaryToBoolValue(findPreference("param0051"));
            SettingsActivity.bindPreferenceSummaryToBoolValue(findPreference("param0052"));
            SettingsActivity.bindPreferenceSummaryToBoolValue(findPreference("param0053"));
            SettingsActivity.bindPreferenceSummaryToBoolValue(findPreference("param0054"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0055"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0056"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0057"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0058"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0059"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param005a"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
            if (paramMenuItem.getItemId() == 16908332) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(paramMenuItem);
        }
    }

    @TargetApi(11)
    public static class Param6PreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.pref_param6);
            setHasOptionsMenu(true);
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0070"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0071"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0072"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0073"));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference("param0074"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
            if (paramMenuItem.getItemId() == 16908332) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(paramMenuItem);
        }
    }

    @TargetApi(11)
    public static class Param7PreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle paramBundle) {
            super.onCreate(paramBundle);
            addPreferencesFromResource(R.xml.pref_param7);
            setHasOptionsMenu(true);
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(DISTANCE));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(PROVINCE));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(CITY));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(VEHICLE_NUMBER));
            SettingsActivity.bindPreferenceSummaryToStrValue(findPreference(VEHICLE_COLOR));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
            if (paramMenuItem.getItemId() == 16908332) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(paramMenuItem);
        }
    }
}
