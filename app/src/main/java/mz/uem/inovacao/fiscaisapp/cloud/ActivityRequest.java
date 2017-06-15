package mz.uem.inovacao.fiscaisapp.cloud;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by SaNogueira on 01/09/2015.
 */
public class ActivityRequest {


    @JsonProperty("code")
    private int code ;

    @JsonProperty("isanounmous")
    private boolean isanounmous ;

    @JsonProperty("picture")
    private String picture ;

    @JsonProperty("description")
    private String description ;

    @JsonProperty("userCode")
    private String userCode;

    @JsonProperty("location_code")
    private int location_code ;

    @JsonProperty("operationDate")
    private long operationDate ;

    public ActivityRequest(boolean isanounmous, String picture, String description, String userCode, int location_code, long operationDate) {
        this.isanounmous = isanounmous;
        this.picture = picture;
        this.description = description;
        this.userCode = userCode;
        this.location_code = location_code;
        this.operationDate = operationDate;
    }

    public int getCode() {
        return code;
    }

    public int getLocation_code() {
        return location_code;
    }

    public void setLocation_code(int location_code) {
        this.location_code = location_code;
    }

    public long getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(long operationDate) {
        this.operationDate = operationDate;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isanounmous() {
        return isanounmous;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setIsanounmous(boolean isanounmous) {
        this.isanounmous = isanounmous;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
