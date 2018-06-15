package io.github.lucasduete.mobileHubApi.infraSecurity.filters;

import io.github.lucasduete.mobileHubApi.infraSecurity.model.NivelAcesso;
import io.github.lucasduete.mobileHubApi.infraSecurity.AcessControll;
import io.github.lucasduete.mobileHubApi.infraSecurity.Security;
import io.github.lucasduete.mobileHubApi.infraSecurity.TokenManagement;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Security
@Provider
@Priority(Priorities.AUTHORIZATION)
public class FilterSecurityAuthorization implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Class<?> classe = resourceInfo.getResourceClass();
        List<NivelAcesso> nivelAcessoClasse = extrairNivelAcesso(classe);

        Method metodo = resourceInfo.getResourceMethod();
        List<NivelAcesso> nivelAcessoMetodo = extrairNivelAcesso(metodo);

        try {

            String token = new TokenManagement().getToken(requestContext);

            if (nivelAcessoMetodo.isEmpty())
                checarPermissoes(nivelAcessoClasse, token);
            else
                checarPermissoes(nivelAcessoMetodo, token);

        } catch (NotAuthorizedException ex) {
            requestContext.abortWith(
                    Response.status(Response.Status.FORBIDDEN).build());
        }
    }

    private List<NivelAcesso> extrairNivelAcesso(AnnotatedElement annotatedElement) {

        if (annotatedElement == null)
            return new ArrayList<NivelAcesso>();
        else {
            Security security = annotatedElement.getAnnotation(Security.class);
            if (security == null)
                return new ArrayList<NivelAcesso>();
            else {
                NivelAcesso[] allowedRoles = security.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checarPermissoes(List<NivelAcesso> niveisAcesso, String login)
            throws NotAuthorizedException {

        if (niveisAcesso.isEmpty())
            return;

        boolean temPermissao = false;
        NivelAcesso nivelPermissaoUsuario = AcessControll.buscarNivelPermissao(login);

        for (NivelAcesso nivelPermissao : niveisAcesso) {
            if (nivelPermissao.equals(nivelPermissaoUsuario)) {
                temPermissao = true;
                break;
            }
        }

        if (!temPermissao)
            throw new NotAuthorizedException(login + " não possui o nível de permissão para esse método");

    }
}
