package com.li.web.boot.exception;

/**
 * @description: 服务接口类
 * @author: LiZl
 * @date: 2022/6/22 21:39
 */
public interface BaseErrorInfoInterface {

    /**
     *  错误码
     * @return
     */
    String getResultCode();

    /**
     * 错误描述
     * @return
     */
    String getResultMsg();
}
