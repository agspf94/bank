package com.example.bank.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Profile("!test")
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    @Qualifier("userDetailsService")
    private lateinit var userDetailsService: UserDetailsService

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .anyRequest().authenticated().and()
            .httpBasic().and()
            .csrf().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        val encoder = BCryptPasswordEncoder()
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder)
        // Security - Mode 2
//            .inMemoryAuthentication().passwordEncoder(encoder)
//            .withUser("user").password(encoder.encode("user")).roles("USER").and()
//            .withUser("admin").password(encoder.encode("admin")).roles("ADMIN", "USER")
    }
}