package com.hardcore.accounting.shiro;

import com.hardcore.accounting.shiro.CustomPathMatchingFilterChainResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;

@Slf4j
public class CustomShiroFilterFactoryBean extends ShiroFilterFactoryBean {
// 把对应的源代码拷贝进来以后,用CustomPathMatchingFilterChainResolver()替换掉原有的PathMatchingFilterChainResolver()
    @Override
    protected AbstractShiroFilter createInstance() throws Exception {
        log.debug("Creating Custom Shiro Filter instance.");
        SecurityManager securityManager = this.getSecurityManager();
        String msg;
        if (securityManager == null) {
            msg = "SecurityManager property must be set.";
            throw new BeanInitializationException(msg);
        } else if (!(securityManager instanceof WebSecurityManager)) {
            msg = "The security manager does not implement the WebSecurityManager interface.";
            throw new BeanInitializationException(msg);
        } else {
            FilterChainManager manager = this.createFilterChainManager();
            // 在此处用自己的代码替换掉原有的代码
            PathMatchingFilterChainResolver chainResolver = new CustomPathMatchingFilterChainResolver();
            chainResolver.setFilterChainManager(manager);
            return new SpringShiroFilter((WebSecurityManager)securityManager, chainResolver);
        }
    }

    // 再把30行爆红的shiroFilter引入进来
    private static final class SpringShiroFilter extends AbstractShiroFilter {
        protected SpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            } else {
                this.setSecurityManager(webSecurityManager);
                if (resolver != null) {
                    this.setFilterChainResolver(resolver);
                }
            }
        }
    }
}
