package com.gch.protobuf;

/**
 * Created by gch on 16-12-23.
 */
public class SubscribeResp {

    private int subRespID;
    private int respCode;
    private String desc;

    public int getSubRespID() {
        return subRespID;
    }

    public void setSubRespID(int subRespID) {
        this.subRespID = subRespID;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
