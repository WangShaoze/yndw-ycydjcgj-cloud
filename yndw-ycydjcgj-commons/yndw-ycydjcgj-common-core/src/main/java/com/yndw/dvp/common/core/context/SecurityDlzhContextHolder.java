package com.yndw.dvp.common.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 登录用户holder
 *
 * 
 * @date 2019/8/5
 */
public class SecurityDlzhContextHolder {
    /**
     * 支持父子线程之间的数据传递
     */
    private static final ThreadLocal<String> CONTEXT = new TransmittableThreadLocal<>();

    public static void setDlzh(String dlzh) {
        CONTEXT.set(dlzh);
    }

    public static String getDlzh() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
