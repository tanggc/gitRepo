package com.tgc.oa.exception;

/**
 * @author: Tgc
 * @date: 2019/8/13
 */
public class OAException extends RuntimeException {
     public OAException(Object obj){
        super(obj.toString());
     }
}
