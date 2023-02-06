/*
 * Copyright 2023 381197562@qq.com
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

package com.liuxz.request.view.inner;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.liuxz.request.model.DataMapping;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DataMappingAddView extends DialogWrapper {
    private JTextField javaTypeTextField;
    private JTextField defaultValueTextField;
    private JPanel panel;

    public DataMappingAddView() {
        super(false);
        init();
        setSize(500, 100);
        setTitle("Add DataMapping");
    }

    protected ValidationInfo doValidate() {
        if(StringUtils.isEmpty(javaTypeTextField.getText())){
            return new ValidationInfo("Please add javaType");
        }
        if(StringUtils.isEmpty(defaultValueTextField.getText())){
            return new ValidationInfo("Please add default value");
        }
        return super.doValidate();
    }

    public DataMapping getValue() {
        return new DataMapping(javaTypeTextField.getText(), defaultValueTextField.getText());
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return panel;
    }



}
