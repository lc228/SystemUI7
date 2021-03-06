/*
 * Copyright (C) 2013 The Android Open Source Project
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
 * limitations under the License.
 */
package com.android.systemui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.tuner.TunerService;

public class BatteryMeterView extends ImageView implements
        BatteryController.BatteryStateChangeCallback, TunerService.Tunable {
    //linwujia edit begin
    //private final BatteryMeterDrawable mDrawable;
    private final ItelBatteryMeterDrawable mDrawable;
    //linwujia edit end
    private final String mSlotBattery;
    private BatteryController mBatteryController;

    //linwujia add begin
    private boolean isReturnCall;
    //linwujia add end

    public BatteryMeterView(Context context) {
        this(context, null, 0);
    }

    public BatteryMeterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryMeterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray atts = context.obtainStyledAttributes(attrs, R.styleable.BatteryMeterView,
                defStyle, 0);
        //linwujia edit begin
        /*final int frameColor = atts.getColor(R.styleable.BatteryMeterView_frameColor,
                context.getColor(R.color.batterymeter_frame_color));*/
        final int frameColor = atts.getColor(R.styleable.BatteryMeterView_frameColor,
                context.getColor(R.color.itel_batterymeter_frame_color));
        //linwujia edit end
        // SPRD: Bug 587470 new construction.
        //mDrawable = new BatteryMeterDrawable(context, new Handler(), frameColor, false);//linwujia edit
        atts.recycle();
        //linwujia add begin
        TypedArray  typeArray = context.obtainStyledAttributes(attrs, R.styleable.returnCall);
        isReturnCall  = typeArray.getBoolean(R.styleable.returnCall_returncall_enabled, false);
        typeArray.recycle();

        mDrawable = new ItelBatteryMeterDrawable(context, new Handler(), frameColor, isReturnCall);
        //linwujia add end
        mSlotBattery = context.getString(
                Resources.getSystem().getIdentifier("status_bar_battery", "string","android"));
        setImageDrawable(mDrawable);
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override
    public void onTuningChanged(String key, String newValue) {
        if (StatusBarIconController.ICON_BLACKLIST.equals(key)) {
            ArraySet<String> icons = StatusBarIconController.getIconBlacklist(newValue);
            setVisibility(icons.contains(mSlotBattery) ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mBatteryController.addStateChangedCallback(this);
        mDrawable.startListening();
        TunerService.get(getContext()).addTunable(this, StatusBarIconController.ICON_BLACKLIST);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mBatteryController.removeStateChangedCallback(this);
        mDrawable.stopListening();
        TunerService.get(getContext()).removeTunable(this);
    }

    @Override
    public void onBatteryLevelChanged(int level, boolean pluggedIn, boolean charging) {
        setContentDescription(
                getContext().getString(charging ? R.string.accessibility_battery_level_charging
                        : R.string.accessibility_battery_level, level));
    }

    @Override
    public void onPowerSaveChanged(boolean isPowerSave) {

    }

    public void setBatteryController(BatteryController mBatteryController) {
        this.mBatteryController = mBatteryController;
        mDrawable.setBatteryController(mBatteryController);
    }

    public void setDarkIntensity(float f) {
        mDrawable.setDarkIntensity(f);
    }
}
