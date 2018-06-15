package io.github.lucasduete.mobileHubApi.infraSecurity.filters;

import java.io.IOException;

import java.security.Principal;
import java.util.logging.Logger;

import javax.annotation.Priority;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import io.github.lucasduete.mobileHubApi.infraSecurity.Security;
import io.github.lucasduete.mobileHubApi.infraSecurity.TokenManagement;
import io.jsonwebtoken.Claims;

@Security
@Provider
@Priority(Priorities.AUTHENTICATION)
public class FilterSecurityAuthentication implements ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		try {
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
				throw new NotAuthorizedException("Necessário Informar Authorization Header Para Acessar Este Recurso.");

			String token = authorizationHeader.substring("Bearer".length()).trim();

			Claims claims = new TokenManagement().validaToken(token);

			if(claims==null)
				throw new NotAuthorizedException("Token inválido");

			modificarRequestContext(requestContext, claims.getIssuer());
		} catch (NotAuthorizedException ex) {
			ex.printStackTrace();
			requestContext.abortWith(
					Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	private void modificarRequestContext(ContainerRequestContext requestContext,String indentificador){

		final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
		
		requestContext.setSecurityContext(new SecurityContext() {
			 @Override
			  public Principal getUserPrincipal() {
			    return () -> indentificador;
			  }

			  @Override
			  public boolean isUserInRole(String role) {
			    return true;
			  }

			  @Override
			  public boolean isSecure() {
			    return currentSecurityContext.isSecure();
			  }

			  @Override
			  public String getAuthenticationScheme() {
			    return "Bearer";
			  }

			});
	}

	//Retorna false se nao for admin, retorna true se for admin
	public static boolean checkAdmin(ContainerRequestContext requestContext) {
		//Passa o Request pelo filtro de Token, se lançar a exeption entao o token não é valido
		try {
			new FilterSecurityAuthentication().filter(requestContext);


			if(!requestContext.getSecurityContext().getUserPrincipal().getName().contains("@"))
				throw new IOException("Não é Administrador");

			return true;

		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			Logger.getLogger("AdministradorController-log").info("Erro:" + ioEx.getStackTrace());
			return false;
		}
	}


	public static boolean checkAluno(ContainerRequestContext requestContext) {
		//Passa o Request pelo filtro de Token, se lançar a exeption entao o token não é valido
		try {
			new FilterSecurityAuthentication().filter(requestContext);

			/*
				Verifica com base no token se é um aluno, apenas administradores posuem email no token
					logo a condição de teste é possuir um "@" no token
			 */
			if(requestContext.getSecurityContext().getUserPrincipal().getName().contains("@"))
				throw new IOException("Não é Aluno");

			return true;

		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			Logger.getLogger("AlunoController-log").info("Erro:" + ioEx.getStackTrace());
			return false;
		}
	}

}
