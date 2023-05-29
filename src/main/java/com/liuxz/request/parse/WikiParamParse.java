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

import com.intellij.psi.PsiType;
import com.liuxz.request.model.ParamNameType;
import it.unimi.dsi.fastutil.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WikiParamParse {
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

    public static final String template = "     \"%s\":\"${%s:%s}\",// \n";

    public Pair<String, String> parseParam(List<ParamNameType> paramNameTypeList) {
        StringBuffer reqSb = new StringBuffer();
        reqSb.append("{\n");
        for (ParamNameType param : paramNameTypeList) {
            String fieldType = param.getType();
            String fieldName = param.getName();
            // sb.append("    // \n");
            Object fieldTypeValue = dataTypes.getOrDefault(fieldType, null);
            if (fieldTypeValue == null) {
                // 非普通类型，可能为List<>
                PsiType psiType = param.getPsiType();
                // List<Object/自定义类型>
                String presentableText = psiType.getPresentableText();
                reqSb.append(template.formatted(fieldName, fieldName, presentableText));
            } else {
                reqSb.append(template.formatted(fieldName, fieldName, fieldTypeValue));
            }
        }
        reqSb.append("}");
        String wikiReqParamStr = reqSb.toString();

        StringBuffer resSb = new StringBuffer();
        resSb.append("{\n");
        resSb.append("  \"data\":{\n");
        resSb.append("      \n");
        resSb.append("  }\n");
        resSb.append("}");
        String wikiResParamStr = resSb.toString();

        // TODO 构造默认参数
        return Pair.of(wikiReqParamStr, wikiResParamStr);
    }
}
