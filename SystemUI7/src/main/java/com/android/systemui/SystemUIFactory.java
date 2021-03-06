/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.systemui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.statusbar.ScrimView;
import com.android.systemui.statusbar.phone.KeyguardBouncer;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.PhoneStatusBar;
import com.android.systemui.statusbar.phone.QSTileHost;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarWindowManager;
import com.android.systemui.statusbar.phoneleather.LeatherAudioProfilesController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardMonitor;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.NetworkController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.statusbar.policy.ZenModeController;

import itel.transsion.settingslib.utils.TalpaUtils;
import itel.transsion.systemui.phoneleather.MtkLeatherAudioProfilesControllerImp;
import itel.transsion.systemui.phoneleather.SprdLeatherAudioProfilesControllerImp;

/**
 * Class factory to provide customizable SystemUI components.
 */
public class SystemUIFactory {
    private static final String TAG = "SystemUIFactory";

    static SystemUIFactory mFactory;

    public static SystemUIFactory getInstance() {
        return mFactory;
    }

    public static void createFromConfig(Context context) {
        final String clsName = context.getString(R.string.config_systemUIFactoryComponent);
        if (clsName == null || clsName.length() == 0) {
            throw new RuntimeException("No SystemUIFactory component configured");
        }

        try {
            Class<?> cls = null;
            cls = context.getClassLoader().loadClass(clsName);
            mFactory = (SystemUIFactory) cls.newInstance();
        } catch (Throwable t) {
            Log.w(TAG, "Error creating SystemUIFactory component: " + clsName, t);
            throw new RuntimeException(t);
        }
    }

    public SystemUIFactory() {}

    public StatusBarKeyguardViewManager createStatusBarKeyguardViewManager(Context context,
            ViewMediatorCallback viewMediatorCallback, LockPatternUtils lockPatternUtils) {
        return new StatusBarKeyguardViewManager(context, viewMediatorCallback, lockPatternUtils);
    }

    public KeyguardBouncer createKeyguardBouncer(Context context, ViewMediatorCallback callback,
            LockPatternUtils lockPatternUtils, StatusBarWindowManager windowManager,
            ViewGroup container) {
        return new KeyguardBouncer(context, callback, lockPatternUtils, windowManager, container);
    }

    public ScrimController createScrimController(ScrimView scrimBehind, ScrimView scrimInFront,
            View headsUpScrim) {
        return new ScrimController(scrimBehind, scrimInFront, headsUpScrim);
    }

    public NotificationIconAreaController createNotificationIconAreaController(Context context,
            PhoneStatusBar phoneStatusBar) {
        return new NotificationIconAreaController(context, phoneStatusBar);
    }

    public QSTileHost createQSTileHost(Context context, PhoneStatusBar statusBar,
            BluetoothController bluetooth, LocationController location,
            RotationLockController rotation, NetworkController network,
            ZenModeController zen, HotspotController hotspot,
            CastController cast, FlashlightController flashlight,
            UserSwitcherController userSwitcher, UserInfoController userInfo,
            KeyguardMonitor keyguard, SecurityController security,
            BatteryController battery, StatusBarIconController iconController,
            NextAlarmController nextAlarmController) {
        return new QSTileHost(context, statusBar, bluetooth, location, rotation, network, zen,
                hotspot, cast, flashlight, userSwitcher, userInfo, keyguard, security, battery,
                iconController, nextAlarmController);
    }

    public <T> T createInstance(Class<T> classType) {
        return null;
    }

    //linwujia add begin
    public LeatherAudioProfilesController createLeatherAudioProfilesController(Context context) {
        LeatherAudioProfilesController leatherAudioProfilesController = null;
        if(TalpaUtils.isSPRDPlatform()) {
            leatherAudioProfilesController = new SprdLeatherAudioProfilesControllerImp(context);
        } else if(TalpaUtils.isMTKPlatform()) {
            leatherAudioProfilesController = new MtkLeatherAudioProfilesControllerImp(context);
        }
        leatherAudioProfilesController.setAudioProfileUpdates(true);
        return leatherAudioProfilesController;
    }
    //linwujia add end
}
