package com.liuxz.request.model.guide;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Date;
import java.util.List;

public class ApiDocModel {
    private boolean needToken;
    private String apiDesc;
    private String apiMethodType;
    private String apiUrl;
    private String apiRequestParam;
    private String apiResponseParam;
    private String apiFileName;
    private String apiFilePath;

    private String apiQuoteText;
    // 中间变量
    private String apiMarkdownHead;

    public boolean isNeedToken() {
        return needToken;
    }

    public void setNeedToken(boolean needToken) {
        this.needToken = needToken;
    }

    public String getApiDesc() {
        return apiDesc;
    }

    public void setApiDesc(String apiDesc) {
        this.apiDesc = apiDesc;
    }

    public String getApiMethodType() {
        return apiMethodType;
    }

    public void setApiMethodType(String apiMethodType) {
        this.apiMethodType = apiMethodType;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiRequestParam() {
        return apiRequestParam;
    }

    public void setApiRequestParam(String apiRequestParam) {
        this.apiRequestParam = apiRequestParam;
    }

    public String getApiResponseParam() {
        return apiResponseParam;
    }

    public void setApiResponseParam(String apiResponseParam) {
        this.apiResponseParam = apiResponseParam;
    }

    public String getApiFileName() {
        return apiFileName;
    }

    public void setApiFileName(String apiFileName) {
        this.apiFileName = apiFileName;
    }

    public String getApiFilePath() {
        return apiFilePath;
    }

    public void setApiFilePath(String apiFilePath) {
        this.apiFilePath = apiFilePath;
    }

    public void setApiMarkdownHead(String apiMarkdownHead) {
        this.apiMarkdownHead = apiMarkdownHead;
    }

    public String getApiMarkdownHead() {
        String todayStr = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
        String apiFileNameBody = apiFileName.replaceAll("Api-", "").replaceAll(".md", "");
        List<String> nameItems = StrUtil.split(apiFileNameBody, "-");
        int size = nameItems.size();
        String markdownPrefix = size > 2 ? "|" + nameItems.get(0) + "|" + nameItems.get(1) + "|" : "|" + nameItems.get(0) + "|";
        return markdownPrefix + "[" + nameItems.get(nameItems.size() - 1) + "](" + "Api-" + apiFileNameBody + ")|R|" + apiDesc + "|:green_apple:|" + todayStr + "|";
    }

    public String getApiQuoteText() {
        return apiQuoteText;
    }

    public void setApiQuoteText(String apiQuoteText) {
        this.apiQuoteText = apiQuoteText;
    }
}
