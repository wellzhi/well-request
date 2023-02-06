/*
 * Copyright 2021 kings1990(darkings1990@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liuxz.request.config;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;

import com.liuxz.request.model.WellRequestConfiguration;
import com.liuxz.request.model.NameGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@State(name = "wellRequest", storages = {@Storage("wellRequest.xml")})
public class WellRequestComponent implements PersistentStateComponent<WellRequestConfiguration> {

    private WellRequestConfiguration config;

    @Nullable
    @Override
    public WellRequestConfiguration getState() {
        if (config == null) {
            config = new WellRequestConfiguration();
            List<String> projectList = new ArrayList<>();
            List<String> envList = new ArrayList<>();
            List<NameGroup> dataList = new ArrayList<>();
            config.setDataList(dataList);
            config.setEnvList(envList);
            config.setProjectList(projectList);
            config.setRandomStringLength(5);
        }
        return config;
    }

    public static WellRequestComponent getInstance() {
        return ApplicationManager.getApplication().getService(WellRequestComponent.class);
    }

    @Override
    public void loadState(@NotNull WellRequestConfiguration state) {
        XmlSerializerUtil.copyBean(state, Objects.requireNonNull(getState()));
    }


}
