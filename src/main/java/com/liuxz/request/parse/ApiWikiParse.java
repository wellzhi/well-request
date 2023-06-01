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

package com.liuxz.request.parse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.intellij.psi.*;
import com.liuxz.request.model.guide.RequestParam;
import com.liuxz.request.model.guide.ResponseParam;
import it.unimi.dsi.fastutil.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiWikiParse {
    public static Map<String, Object> dataTypes = new HashMap<>();

    static {
        dataTypes.put("byte", "byte");
        dataTypes.put("java.lang.Byte", "Byte");
        dataTypes.put("short", "short");
        dataTypes.put("java.lang.Short", "Short");
        dataTypes.put("int", "int");
        dataTypes.put("java.lang.Integer", "Integer");
        dataTypes.put("long", "long");
        dataTypes.put("java.lang.Long", "Long");
        dataTypes.put("char", "char");
        dataTypes.put("java.lang.Character", "Character");
        dataTypes.put("float", "float");
        dataTypes.put("java.lang.Float", "Float");
        dataTypes.put("double", "double");
        dataTypes.put("java.lang.Double", "Double");
        dataTypes.put("boolean", "boolean");
        dataTypes.put("java.lang.Boolean", "Boolean");
        dataTypes.put("java.math.BigDecimal", "BigDecimal");
        dataTypes.put("java.lang.String", "String");

        // 业务项目date类型参数都转换为long
        dataTypes.put("java.util.Date", "long");
        dataTypes.put("java.sql.Date", "long");
        dataTypes.put("java.sql.Timestamp", "long");
        dataTypes.put("java.time.LocalDate", "long");
        dataTypes.put("java.time.LocalTime", "long");
        dataTypes.put("java.time.LocalDateTime", "long");
        dataTypes.put("java.time.YearMonth", "long");
    }

    public static Map<String, Object> defaultValues = new HashMap<>();

    static {
        defaultValues.put("byte", 1);
        defaultValues.put("java.lang.Byte", 1);
        defaultValues.put("short", 1);
        defaultValues.put("java.lang.Short", 1);
        defaultValues.put("int", 1);
        defaultValues.put("java.lang.Integer", 1);
        defaultValues.put("long", 1);
        defaultValues.put("java.lang.Long", 1);
        defaultValues.put("char", 'a');
        defaultValues.put("java.lang.Character", 'a');
        defaultValues.put("float", 0.01F);
        defaultValues.put("java.lang.Float", 0.01F);
        defaultValues.put("double", 0.01D);
        defaultValues.put("java.lang.Double", 0.01D);
        defaultValues.put("boolean", true);
        defaultValues.put("java.lang.Boolean", true);
        defaultValues.put("java.math.BigDecimal", 0.01);
        defaultValues.put("java.lang.String", "\"" + RandomUtil.randomNumbers(2) + "\"");

        // 业务项目date类型参数都转换为long
        defaultValues.put("java.util.Date", DateUtil.current());
        defaultValues.put("java.sql.Date", DateUtil.current());
        defaultValues.put("java.sql.Timestamp", DateUtil.current());
        defaultValues.put("java.time.LocalDate", DateUtil.current());
        defaultValues.put("java.time.LocalTime", DateUtil.current());
        defaultValues.put("java.time.LocalDateTime", DateUtil.current());
        defaultValues.put("java.time.YearMonth", DateUtil.current());
    }



    public enum GuideExpression {
        getOptionalIntField(false), getRequiredIntField(true), getOptionalTextField(false), getRequiredTextField(true), getOptionalBooleanField(false), getRequiredBooleanField(true), getOptionalLongField(false), getRequiredLongField(true), getOptionalDoubleField(false), getRequiredDoubleField(true), getRequiredBigDecimalField(true), getObject(true), getOptionalObject(false), getObjects(true), getOptionalObjects(false), getMap(false);

        private final boolean required;

        GuideExpression(boolean required) {
            this.required = required;
        }

        public static final boolean isGuideExpression(String expression) {
            for (GuideExpression value : GuideExpression.values()) {
                if (value.name().equals(expression)) {
                    return true;
                }
            }
            return false;
        }

        public static final Boolean isRequired(String expression) {
            for (GuideExpression value : GuideExpression.values()) {
                if (value.name().equals(expression)) {
                    return value.required;
                }
            }
            return null;
        }
    }

    public static final String request_template = "     \"%s\":\"${%s:%s}\",// \n";
    public static final String response_template = "        \"%s\":\"${%s:%s}\", \n";
    public static final String template_default_value = "     \"%s\":%s, \n";

    public static String createApiRequestParamDefaultParamWiki(List<RequestParam> requestParams) {
        if (CollUtil.isEmpty(requestParams)){
            return "{}";
        }
        StringBuffer defaultValueSb = new StringBuffer();
        defaultValueSb.append("{\n");
        for (RequestParam param : requestParams) {
            String fieldType = param.getFieldType();
            String fieldName = param.getFieldName();
            Object defaultValue = defaultValues.getOrDefault(fieldType, null);
            if (defaultValue == null) {
                // 非普通类型，可能为List<>
                PsiType psiType = param.getPsiType();
                // List<Object/自定义类型>
                defaultValueSb.append(template_default_value.formatted(fieldName, "[]"));
            } else {
                defaultValueSb.append(template_default_value.formatted(fieldName, defaultValue));
            }
        }
        defaultValueSb.append("}");
        return defaultValueSb.toString();
    }
    public static final String createApiResponseParamWiki(List<ResponseParam> responseParams) {
        if (CollUtil.isEmpty(responseParams)){
            return "{}";
        }
        StringBuffer responseSb = new StringBuffer();
        responseSb.append("{\n");
        responseSb.append("  \"data\":{\n");
        for (ResponseParam requestParam : responseParams) {
            String fieldType = requestParam.getFieldType();
            String fieldName = requestParam.getFieldName();
            PsiType psiType = requestParam.getPsiType();
            Object fieldTypeValue = dataTypes.getOrDefault(fieldType, null);
            if (fieldTypeValue == null) {
                // List<Object/自定义类型>
                String presentableText = psiType.getPresentableText();
                responseSb.append(response_template.formatted(fieldName, fieldName, presentableText));
            } else {
                responseSb.append(response_template.formatted(fieldName, fieldName, fieldTypeValue));
            }
        }
        responseSb.append("  }\n");
        responseSb.append("}");
        return responseSb.toString();
    }

    public static final String createApiRequestParamWiki(List<RequestParam> requestParams) {
        if (CollUtil.isEmpty(requestParams)){
            return "{}";
        }
        StringBuffer requestSb = new StringBuffer();
        requestSb.append("{\n");
        for (RequestParam requestParam : requestParams) {
            String fieldType = requestParam.getFieldType();
            String fieldName = requestParam.getFieldName();
            PsiType psiType = requestParam.getPsiType();
            Object fieldTypeValue = dataTypes.getOrDefault(fieldType, null);
            if (fieldTypeValue == null) {
                // List<Object/自定义类型>
                String presentableText = psiType.getPresentableText();
                requestSb.append(request_template.formatted(fieldName, fieldName, presentableText));
            } else {
                requestSb.append(request_template.formatted(fieldName, fieldName, fieldTypeValue));
            }
        }
        requestSb.append("}");
        return requestSb.toString();
    }

    public static final Pair<List<RequestParam>, List<ResponseParam>> getApiParamsPair(PsiMethod psiMethod) {
        List<RequestParam> requestParams = new ArrayList<>();
        List<ResponseParam> responseParams = new ArrayList<>();
        PsiCodeBlock body = psiMethod.getBody();
        PsiStatement[] statements = body.getStatements();
        for (PsiStatement statement : statements) {
            if (!(statement != null && statement instanceof PsiDeclarationStatement)) {
                continue;
            }
            PsiDeclarationStatement declarationStatement = (PsiDeclarationStatement) statement;
            PsiElement[] declaredElements = declarationStatement.getDeclaredElements();
            for (PsiElement declaredElement : declaredElements) {
                if (!(declaredElement != null && declaredElement instanceof PsiLocalVariable)) {
                    continue;
                }
                PsiLocalVariable localVariable = (PsiLocalVariable) declaredElement;
                PsiElement[] children = localVariable.getChildren();
                for (PsiElement child : children) {
                    if (!(child instanceof PsiMethodCallExpression)) {
                        continue;
                    }
                    PsiMethodCallExpression psiMethodCallExpression = (PsiMethodCallExpression) child;
                    PsiReferenceExpression methodExpression = psiMethodCallExpression.getMethodExpression();
                    String referenceName = methodExpression.getReferenceName();
                    boolean isGuideExpression = GuideExpression.isGuideExpression(referenceName);

                    String fieldName = localVariable.getName();
                    PsiType psiType = localVariable.getType();
                    String fieldType = psiType.getCanonicalText();
                    if (!isGuideExpression) {
                        ResponseParam responseParam = new ResponseParam();
                        responseParam.setFieldName(fieldName);
                        responseParam.setFieldType(fieldType);
                        responseParam.setPsiType(psiType);
                        responseParams.add(responseParam);
                    } else {
                        RequestParam requestParam = new RequestParam();
                        requestParam.setFieldName(fieldName);
                        requestParam.setFieldType(fieldType);
                        requestParam.setPsiType(psiType);
                        requestParam.setRequired(GuideExpression.isRequired(referenceName));
                        requestParam.setDefaultValue(defaultValues.get(fieldType));
                        requestParams.add(requestParam);
                    }
                }
            }

        }
        return Pair.of(requestParams, responseParams);
    }
}
