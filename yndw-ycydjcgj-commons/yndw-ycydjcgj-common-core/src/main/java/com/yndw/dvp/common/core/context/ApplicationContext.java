package com.yndw.dvp.common.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *  * Description: 应用上下文<br>
 *  * Details: 在普通类中获取容器管理的 Bean.
 * Create By Carlos
 * 2020/12/21
 */
@Component
public final class ApplicationContext implements ApplicationContextAware {

    private static org.springframework.context.ApplicationContext context;

    /**
     * Description: 手动获取容器中的 Bean
     *
     * @param genericBeanClass Bean 的 Class 对象
     * @return GenericBean
     * @author LiKe
     * @date 2020-08-10 15:34:12
     */
    public static <GenericBean> GenericBean getBean(Class<GenericBean> genericBeanClass) {
        return context.getBean(genericBeanClass);
    }

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
