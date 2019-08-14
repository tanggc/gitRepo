package com.tgc.oa.exception;

/**
 * @author: Tgc
 * @date: 2019/8/13
 */
public class ExceptionTest {
    public static void main(String[] args) {
        Object obj = "qqq";
        if(obj == null){
            throw new OAException(ErrorCode.NULL_OBJ);
        }
        if(obj.equals("qqq")){
            throw new OAException(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
