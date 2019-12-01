package com.ssbrasilprev.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // Cria 2 usuarios para teste
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
    }

    // protege os endpoins com :HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/clientes/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/clientes").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/clientes/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/clientes/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/clientes/**").hasRole("ADMIN")

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/pedidos/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/pedidos").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/pedidos/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/pedidos/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/pedidos/**").hasRole("ADMIN")

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/categorias/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/categorias").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/categorias/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/categorias/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/categorias/**").hasRole("ADMIN")

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/pedidoitens/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/pedidoitens").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/pedidoitens/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/pedidoitens/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/pedidoitens/**").hasRole("ADMIN")

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/produtos/**").hasRole("USER").and().formLogin().and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/produtos/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/produtos/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN")
                
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/h2-console/*").permitAll().and().formLogin().disable()
                .headers().frameOptions().disable() //h2
                ;
    }

}
