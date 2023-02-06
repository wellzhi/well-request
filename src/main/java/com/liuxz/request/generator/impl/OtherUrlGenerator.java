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

package com.liuxz.request.generator.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;


import com.liuxz.request.config.WellRequestComponent;
import com.liuxz.request.model.WellRequestConfiguration;
import com.liuxz.request.model.ParamNameType;
import com.liuxz.request.generator.FastUrlGenerator;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class OtherUrlGenerator extends FastUrlGenerator {

    @Override
    public String generate(PsiElement psiElement) {
        WellRequestConfiguration config = WellRequestComponent.getInstance().getState();
        assert config != null;
        String domain = config.getDomain();
        if (!(psiElement instanceof PsiMethod)) {
            return StringUtils.EMPTY;
        }
        PsiMethod psiMethod = (PsiMethod) psiElement;


        return null;
    }

    @Override
    public String getMethodRequestMappingUrl(PsiMethod psiMethod) {
        return null;
    }

    @Override
    public String getClassRequestMappingUrl(PsiMethod psiMethod) {
        return null;
    }

    @Override
    public List<ParamNameType> getMethodUrlParamList(PsiMethod psiMethod) {
        return null;
    }

    @Override
    public List<ParamNameType> getMethodBodyParamList(PsiMethod psiMethod) {
        return null;
    }

    @Override
    public String getMethodDescription(PsiMethod psiMethod) {
        return null;
    }

    @Override
    public String getMethodType(PsiMethod psiMethod) {
        return null;
    }
}
