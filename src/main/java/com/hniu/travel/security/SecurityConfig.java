package com.hniu.travel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//Security配置类
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //Spring security配置

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 自定义表单登录
        http.formLogin()
                .loginPage("/backstage/admin_login") // 自定义登录页面
                .usernameParameter("username") // 用户名项
                .passwordParameter("password") // 密码项
                .loginProcessingUrl("/backstage/admin/login") // 登录提交路径，提交后执行认证逻辑
                .successForwardUrl("/backstage/index") // 登录成功后跳转路径
                .failureForwardUrl("/backstage/admin_fail"); // 登录失败后跳转路径

        // 权限拦截配置
        http.authorizeRequests()
                .antMatchers("/backstage/admin/login").permitAll() // 登录不需要认证
                .antMatchers("/backstage/admin_fail").permitAll() // 登录失败不需要认证
                .antMatchers("/backstage/admin_login").permitAll() // 登录页不需要认证
                .antMatchers("/**/*.css","/**/*.js","/*.png").permitAll() //放行静态资源
                .antMatchers("/backstage/**").authenticated();//其余都需要认证

       //退出登陆
        http.logout()
                .logoutUrl("/backstage/logout")
                .logoutSuccessUrl("/backstage/admin_login")
                .clearAuthentication(true) //清除认证信息
                .invalidateHttpSession(true); //清除session

        // 异常处理
        http.exceptionHandling()
//                .accessDeniedPage("/backstage/no_permission"); // 权限不足处理器
                .accessDeniedHandler(new MyAccessDeniedHandler()); // 权限不足处理器

        // 关闭csrf防护
        http.csrf().disable();

        //开启跨域访问
        http.cors();


    }

    // 密码编码器
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
