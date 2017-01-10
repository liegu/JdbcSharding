package com.petesky.jdbcSharding.bean;

/**
 * Desc:
 * Created by liegu.pete on 2016/8/6 下午4:13.
 */
public enum ShardingTypeEnum {
    HASH(1,"hash"),
    MOD(2,"mod");


    private int code;
    private String text;

    ShardingTypeEnum (int code,String text){
        this.code=code;
        this.text=text;
    }

    public static ShardingTypeEnum codeOf(int code) {

        switch (code) {
            case 1:
                return HASH;
            case 2:
                return MOD;


        }
        return null;
    }

    public static ShardingTypeEnum textOf(String text) {

        if(HASH.getText().equals(text)){
            return HASH;
        }else if(MOD.getText().equals(text)){
            return MOD;
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
