package com.hardcore.accounting.shiro;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class CustomPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {
    // 复制过来原getChain()代码,在此基础上进行修改
    @Override
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = this.getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        }
        // currentPath是从请求中拿到的   pathPattern是从filter中拿到的,提前设置好的路径
        String currentPath = getPathWithinApplication(request);
        log.debug("current path in CustomPathMatchingFilterChainResolver:{}",currentPath);
        for (String pathPattern : filterChainManager.getChainNames()){
            log.debug("pathPattern:{}",pathPattern);
            // 如果二者相匹配,那么就通过验证
            if(isHttpRequestMatched(pathPattern,currentPath,request)){
                return filterChainManager.proxy(originalChain, pathPattern);
            }
        }
        return null;
    }

    /**
     * 判断method的请求与需求的请求是否一致的程序
     *
     * 1. 比较request的url
     * 2. 如果存在 http method 的 Mark,就比较 http 的 method
     *
     * 判定输入的chain url的前一半,和请求是否一致的程序
     */
    private boolean isHttpRequestMatched(String chain, String currentPath, ServletRequest request) {
        val array = chain.split("::");
        val url = array[0];
        boolean isHttpMethodMatched = true;
        if (array.length > 1) {
            val methodInRequest = ((HttpServletRequest) request).getMethod().toUpperCase();
            val method = array[1];
            isHttpMethodMatched = method.equals(methodInRequest);
        }
        return pathMatches(url, currentPath) && isHttpMethodMatched;
    }
}
