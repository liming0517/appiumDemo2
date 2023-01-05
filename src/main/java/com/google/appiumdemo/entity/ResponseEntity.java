package com.google.appiumdemo.entity;

/**
 * The json object returned to the frontend
 * success: status =2000
 * failed: status = 5000
 */
public class ResponseEntity {

    private Integer status;
    private String message;
    private Object obj;

    public ResponseEntity(Integer status,String message,Object obj){
        this.status =status;
        this.message=message;
        this.obj = obj;
    }

    public static ResponseEntity success(String message,Object obj){
        return new ResponseEntity(2000,message,obj);
    }

    public static ResponseEntity failed(String message,Object obj){
        return new ResponseEntity(5000,message,obj);
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }




}
