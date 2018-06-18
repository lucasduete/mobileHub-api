package io.github.lucasduete.mobileHubApi.infraSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;
import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.Date;

public class TokenManagement {

    private final String SECRETKEY = "LuC4sDu3tE#M0b1l3-HUb-$P1(!)@K3Y/CR1PT";

    public String gerarToken(String login, int limiteDias) {
        //Gera algoritmo de criptografia em SHA512
        try {
            SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;

            Date agora = new Date();

            Calendar expira = Calendar.getInstance();
            expira.add(Calendar.DAY_OF_MONTH, limiteDias);

            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRETKEY);

            SecretKeySpec key = new SecretKeySpec(apiKeySecretBytes, algorithm.getJcaName());

            JwtBuilder construtor = Jwts.builder()
                    .setIssuedAt(agora)
                    .setIssuer(login)
                    .signWith(algorithm, key)
                    .setExpiration(expira.getTime());
            return construtor.compact();


        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Claims validaToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRETKEY))
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static String getToken(SecurityContext securityContext) {

        return securityContext
                .getUserPrincipal()
                .getName();
    }

    public static String getToken(ContainerRequestContext requestContext) {

        return requestContext
                .getSecurityContext()
                .getUserPrincipal()
                .getName();
    }
}
