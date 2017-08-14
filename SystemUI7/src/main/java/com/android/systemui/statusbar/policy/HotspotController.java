/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.android.systemui.statusbar.policy;

public interface HotspotController {
    void addCallback(Callback callback);
    void removeCallback(Callback callback);
    boolean isHotspotEnabled();
    void setHotspotEnabled(boolean enabled);
    boolean isHotspotSupported();
    // talpa@andy 2017/5/27 10:14 add:解决cdn#10282 切换关闭WiFi热点时，
    // 偶现WiFi热点一直在闪动 @{
    int getHotspotState();
    // @}

    public interface Callback {
        void onHotspotChanged(boolean enabled, int state);
    }
}