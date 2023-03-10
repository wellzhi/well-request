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

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.liuxz.request.model.WellRequestCurrentProjectConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@State(name = "wellRequestCurrentProjectConfig", storages = {@Storage("wellRequestCurrentProjectConfig.xml")})
public class WellRequestCurrentProjectConfigComponent implements PersistentStateComponent<WellRequestCurrentProjectConfiguration> {
    private WellRequestCurrentProjectConfiguration config;

    @Override
    public @Nullable WellRequestCurrentProjectConfiguration getState() {
        if (config == null) {
            config = new WellRequestCurrentProjectConfiguration();
        }
        return config;
    }

    @Override
    public void loadState(@NotNull WellRequestCurrentProjectConfiguration state) {
        XmlSerializerUtil.copyBean(state, Objects.requireNonNull(getState()));
    }

    public static WellRequestCurrentProjectConfigComponent getInstance(Project project) {
        return project.getService(WellRequestCurrentProjectConfigComponent.class);
    }
}
