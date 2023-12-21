package com.axway.apim.eventlog;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;


@JsonPropertyOrder({ "APIName", "APIMethodName", "TransactionDate","Path","TransactionStatus","BackendHost","BackendDuration","OverallDuration" })

public class Transaction {

    public String path;
    public String apiName;
    public String apiMethodName;

    public Date date;
    public int overallDuration;

    public String backendHost;

    public int backendTime;
    public String status;

    @JsonGetter("Path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonGetter("APIName")
    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    @JsonGetter("APIMethodName")
    public String getApiMethodName() {
        return apiMethodName;
    }

    public void setApiMethodName(String apiMethodName) {
        this.apiMethodName = apiMethodName;
    }

    @JsonGetter("TransactionDate")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonGetter("OverallDuration")
    public int getOverallDuration() {
        return overallDuration;
    }

    public void setOverallDuration(int overallDuration) {
        this.overallDuration = overallDuration;
    }

    @JsonGetter("BackendHost")

    public String getBackendHost() {
        return backendHost;
    }

    public void setBackendHost(String backendHost) {
        this.backendHost = backendHost;
    }

    @JsonGetter("BackendDuration")

    public int getBackendTime() {
        return backendTime;
    }

    public void setBackendTime(int backendTime) {
        this.backendTime = backendTime;
    }

    @JsonGetter("TransactionStatus")

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "path='" + path + '\'' +
            ", apiName='" + apiName + '\'' +
            ", apiMethodName='" + apiMethodName + '\'' +
            ", date=" + date +
            ", overallDuration=" + overallDuration +
            ", backendHost='" + backendHost + '\'' +
            ", backendTime=" + backendTime +
            ", status='" + status + '\'' +
            '}';
    }
}
