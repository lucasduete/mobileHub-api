package io.github.lucasduete.mobileHubApi.infraSecurity;

import io.github.lucasduete.mobileHubApi.infraSecurity.model.NivelAcesso;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Security {

    NivelAcesso[] value() default{};
}