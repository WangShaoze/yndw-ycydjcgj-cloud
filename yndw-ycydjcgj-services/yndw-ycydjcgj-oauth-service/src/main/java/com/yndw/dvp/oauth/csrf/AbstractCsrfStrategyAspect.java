package com.yndw.dvp.oauth.csrf;

import com.yndw.dvp.common.core.exception.AmbiguousCsrfStrategyException;
import com.yndw.dvp.common.core.utils.JsonUtil;
import com.yndw.dvp.oauth.csrf.strategy.AbstractCsrfStrategy;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Create By Carlos
 * 2020/12/18
 */
public abstract class AbstractCsrfStrategyAspect {
    /**
     * Description: 选举合适的策略
     *
     * @param parameters 用于策略选举的参数
     * @return {@link AbstractCsrfStrategy} 的实现类
     * @author LiKe
     * @date 2020-08-11 19:00:26
     */
    protected AbstractCsrfStrategy decide(Map<String, ?> parameters, List<? extends AbstractCsrfStrategy> delegates) {
        // ~ 选举出匹配的策略
        final List<? extends AbstractCsrfStrategy> candidates = delegates.stream().filter(delegate -> delegate.supports(parameters)).collect(Collectors.toList());
        final int eligibleStrategyNum = candidates.size();

        if (eligibleStrategyNum == 0) {
            return null;
        } else if (eligibleStrategyNum > 1) {
            // ~ 有匹配的多个策略
            throw new AmbiguousCsrfStrategyException(eligibleStrategyNum);
        } else {
            // ~ 返回合适的策略
            return candidates.get(0);
        }
    }

    protected Map<String, ?> extractParameters(ProceedingJoinPoint proceedingJoinPoint) {
        return JsonUtil.toMap(JsonUtil.toJSONString(proceedingJoinPoint.getArgs()[1]));
    }
}
