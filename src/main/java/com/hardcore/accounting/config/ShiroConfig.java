package com.hardcore.accounting.config;

import com.hardcore.accounting.shiro.CustomShiroFilterFactoryBean;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    private static final String HASH_ALGORITHM_NAME = "SHA-256";
    private static final int HASH_ITERATIONS = 1000;

    /**
     * securityManager: 连接realm和subject部分的主体,并向其中注入realm
     * The bean for Security manager.
     *
     * @param realm the specific realm.
     * @return Security manager.
     */
    @Bean
    public SecurityManager securityManager(Realm realm) {
        val securityManager = new DefaultWebSecurityManager(realm);
        SecurityUtils.setSecurityManager(securityManager);
        return new DefaultWebSecurityManager();
    }

    /**
     * filter: 目的是实现权限相关的拦截.
     * The bean for security manager.
     *
     * @param securityManager security manager.
     * @return Shiro filter factory bean.
     *
     * anon: 无需login就能Access
     * authc: 需要login才能Access
     * user: 用readMe记住我,就能直接访问.
     * role: 必须要得到的相关的role后才能访问
     *
     * 创建FactoryBean并放到传进去的securityManager里面,再从FactoryBean中提取filter,并对他进行编辑
     * 创建Filter的map,并对它编辑url的访问条件.以此来实现对不同url的筛选
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        // CustomShiroFilterFactoryBean(): 经过修改后的ShiroFilterFactoryBean()
        val shiroFilterFactoryBean = new CustomShiroFilterFactoryBean();

        // 定义一些url规则和对应的访问条件(进行筛选功能的主体)
        val shiroFilterDefinitionMap = new LinkedHashMap<String, String>();
        //@Todo:不同的http可能需要不同的filter
        shiroFilterDefinitionMap.put("/v1.0/users", "anon");
        shiroFilterDefinitionMap.put("/v1.0/session", "anon");
        shiroFilterDefinitionMap.put("/v1.0/tags/**", "authc");
        shiroFilterDefinitionMap.put("/v1.0/**", "authc");
        shiroFilterDefinitionMap.put("/v1.0/records/**", "authc");
        shiroFilterDefinitionMap.put("/v1.0/users/**::POST", "custom");

        //swagger related url.
        shiroFilterDefinitionMap.put("/swagger-ui.html/**", "anon");
        shiroFilterDefinitionMap.put("/swagger-resources/**", "anon");
        shiroFilterDefinitionMap.put("/v2/**", "anon");
        shiroFilterDefinitionMap.put("/webjars/**", "anon");

        shiroFilterDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * HashedCredentialsMatcher作用: 账户和密码的验证机制
     *
     * AlgorithmName给创建好的matcher对象命名,Iterations设置迭代次数,设置好以后return出matcher
     **/
    @Bean
    public HashedCredentialsMatcher matcher() {
        val matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(HASH_ALGORITHM_NAME);
        matcher.setHashIterations(HASH_ITERATIONS);
        matcher.setHashSalted(true);
        matcher.setStoredCredentialsHexEncoded(false);
        return matcher;
    }
}
