package com.liveperson.faas.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liveperson.faas.csds.CsdsClient;
import com.liveperson.faas.exception.CsdsRetrievalException;
import com.liveperson.faas.exception.TokenGenerationException;
import com.liveperson.faas.http.RestClient;
import com.liveperson.faas.security.types.BearerConfigResponseObject;
import com.liveperson.faas.security.types.BearerResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthBearerGenerator implements BearerGenerator {

    static Logger logger = LogManager.getLogger();
    String accountId;
    String username;
    String password;
    private RestClient restClient;
    private CsdsClient csdsClient;
    private BearerResponse currentAuth;
    private AuthExpiryTester authExpiryTester;

    public AuthBearerGenerator(RestClient restClient, CsdsClient csdsClient, String accountId,
                              String username,
                              String password) {
        this.restClient = restClient;
        this.csdsClient = csdsClient;
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.authExpiryTester = new JwtExpiryTester();

    }

    @Override
    public String retrieveBearerToken() throws TokenGenerationException {
        if (jwtNotInitializedOrAboutToExpire()) {
            try {
                currentAuth = generateAuthBearer(accountId, username, password);
            } catch (CsdsRetrievalException | IOException e) {
                logger.warn("There was an error retrieving Bearer from Server. " + e);
                if (jwtFailedToInitializeOrExpired()) {
                    throw new TokenGenerationException("No valid Bearer could be retrieved and current one is expired", e);
                }
            }
        }

        return "Bearer " + this.currentAuth.getBearer();
    }
    public BearerConfigResponseObject retrieveBearerConfig() throws TokenGenerationException{
        if(this.currentAuth == null){
            throw new TokenGenerationException("No valid Bearer could be retrieved and current one is expired");
        }

        return this.currentAuth.getConfig();
    }

    private BearerResponse generateAuthBearer(String accountId, String username, String password) throws CsdsRetrievalException,
            IOException {
        String sentinelBaseUrl = csdsClient.getDomain("agentVep");
        String jwtUrl = String.format("https://%s/api/account/%s/login" +
                        "?v=1.3",
                sentinelBaseUrl, accountId);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/x-www-form-urlencoded");
        String body = String.format("username=%s&password=%s", username, password);
        String response = restClient.post(jwtUrl, headers, body);
        ObjectMapper mapper = new ObjectMapper();
        BearerResponse bearerData = mapper.readValue(response, new TypeReference<BearerResponse>() {
        });
        return bearerData;
    }

    private boolean jwtNotInitializedOrAboutToExpire() {
        return currentAuth == null || authExpiryTester.isAboutToExpire(currentAuth.getBearer());
    }

    private boolean jwtFailedToInitializeOrExpired() {
        return currentAuth == null || authExpiryTester.isExpired(currentAuth.getBearer());
    }

}
