package com.adnstyle.dalnu_gw.config;

import com.adnstyle.dalnu_gw.filter.AuthCustomFilter;
import com.adnstyle.dalnu_gw.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    
    private final CorsConfig corsConfig;
    
    private final AuthCustomFilter authCustomFilter;
    
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        return http
            .httpBasic().disable()
            .addFilter(corsConfig.corsFilter())
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션사용안함
            .and()
            .formLogin().disable()
            .apply(authCustomFilter)
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
//            .authorizeRequests()
//            .antMatchers("/test").hasAnyRole("USER","TEST")
//            .antMatchers("/hello").hasAnyRole("ADMIN","MANAGER")
//            .antMatchers("/", "/member/**", "/user/**", "/jyHome","/selectUserData","/selectUserRoles","/selectUserDB").permitAll()
//            .anyRequest().permitAll()//모든 권한 다 허용
//            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .deleteCookies("Authorization")
            .and()
            .build();
        
    }
    
    @Bean
    public WebSecurityCustomizer configure(){ // 시큐리티에서 제외할것들(필터를 타지 않게 하려는것들 등록)
        return (web) -> web.ignoring().mvcMatchers(
            "/","/member/failLoginForm","/member/loginForm"
        );
    }
}

/**
 * apply() 메소드를 사용해서 추가한 커스텀 필터는 어느시점에 동작하나??
 *
 * 커스텀 필터가 UsernamePasswordAuthenticationFilter 또는
 * AbstractAuthenticationProcessingFilter 같은 인증 필터를 상속받았다면
 * 해당 커스텀 필터는 인증 필터가 실행되어야 하는 시점에 실행된다.
 *
 * 요약 : 커스텀 필터가 필터 체인의 맨 뒤에 추가되었더라도 커스텀 필터가 특정한 필터를 상속받았다면,
 * 상속받은 필터가 동작해야 하는 시점에 실행된다.
 */
    

