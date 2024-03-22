package com.liveperson.faas.security;

import com.liveperson.faas.exception.TokenGenerationException;
import com.liveperson.faas.exception.DpopJwtGenerationException;

/**
 * Generates a Oauth2 + DPop for authentication purposes on LivePerson internal systems
 *
 * @author sschwarz
 */
public interface AuthDPoPSignatureBuilder {

    /**
     * Generate Oauth2 access token string
     * 
     * @param domainUrl
     * @throws TokenGenerationException when token generation fails
     * @return the access token
     */
    String getAccessTokenInternal(String domainUrl) throws TokenGenerationException;

    /**
     * Generate the DPoP header
     * 
     * @param url
     * @param method
     * @param accessToken
     * @return
     * @throws DpopJwtGenerationException
     */
    String getDpopHeaderInternal(String url, String method, String accessToken) throws DpopJwtGenerationException;

}
