package com.husher.httpclient;


import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SkipVerifyRestTemplateBuilder {

    private Logger logger = LoggerFactory.getLogger(SkipVerifyRestTemplateBuilder.class);

    /**
     * 初始化ssl resttemplate
     * @return
     */
    @Bean("skipVerifyRestTemplate")
    public RestTemplate skipVerifyRestTemplate() {
        RestTemplate rest = new RestTemplate();

        SSLConnectionSocketFactory buildSSLSocketFactory = null;
        try {
            buildSSLSocketFactory = this.buildSSLSocketFactory();
        } catch (Exception e) {
            logger.error("", e);
        }

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                HttpClients.custom().setSSLSocketFactory(buildSSLSocketFactory).build());

        factory.setConnectionRequestTimeout(1000);
        factory.setConnectTimeout(1000);

        rest.setRequestFactory(factory);
        return rest;
    }

    private SSLConnectionSocketFactory buildSSLSocketFactory() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        // 设置信任证书（绕过TrustStore验证）
        sslContext.init(null, new TrustManager[] { new AuthX509TrustManager() }, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                new String[] { "TLSv1" }, null, new HostnameVerifier() {
            // hostname,默认返回true,不验证hostname
            @Override
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        });
        return sslConnectionSocketFactory;
    }

    private class AuthX509TrustManager implements TrustManager, X509TrustManager {
        @Override public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        @Override public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
            return;
        }
    }
}
