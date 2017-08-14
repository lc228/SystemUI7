/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.android.systemui.recents.events.ui.dragndrop;

import android.graphics.Point;

import com.android.systemui.recents.events.EventBus;
import com.android.systemui.recents.model.Task;
import com.android.systemui.recents.views.TaskTalpaView;
import com.android.systemui.recents.views.TaskView;

/**
 * This event is sent whenever a drag starts.
 */
public class DragTalpaStartEvent extends EventBus.Event {

    public final Task task;
    public final TaskTalpaView taskView;
    public final Point tlOffset;

    public DragTalpaStartEvent(Task task, TaskTalpaView taskView, Point tlOffset) {
        this.task = task;
        this.taskView = taskView;
        this.tlOffset = tlOffset;
    }
}