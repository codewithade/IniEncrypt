package com.smatworld.iniencrypt.models;

public class TaskData<T> {
    private T data;
    private TaskStatus mTaskStatus;
    private String errorMessage;
    private String successMessage;
    private long startTime;
    private long endTime;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TaskStatus getTaskStatus() {
        return mTaskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        mTaskStatus = taskStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
