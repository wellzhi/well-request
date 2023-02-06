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

package com.liuxz.request.configurable;

import com.intellij.ide.util.gotoByName.ChooseByNameFilterConfiguration;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;

@Service
@State(name = "WellRequestSearchEverywhereConfiguration", storages = @Storage(StoragePathMacros.CACHE_FILE))
public final class WellRequestSearchEverywhereConfiguration extends ChooseByNameFilterConfiguration<String> {

    public static WellRequestSearchEverywhereConfiguration getInstance() {
        return ApplicationManager.getApplication().getService(WellRequestSearchEverywhereConfiguration.class);
    }

    @Override
    protected String nameForElement(String type) {
        return type;
    }
}