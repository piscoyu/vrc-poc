package com.daimler.util;

import okhttp3.*;
import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

//import org.springframework.http.MediaType;

public class HttpUtils {
    /**
     * post request through application/x-www-form-urlencoded type request
     *
     * @param url
     * @param postData
     * @return
     */
    public static String post(String url, String postData) {
        return post(url, postData, null, null);
    }

    /**
     * post request through application/x-www-form-urlencoded type request
     *
     * @param url
     * @param postData
     * @param proxyUrl
     * @param proxyPort
     * @return
     */
    public static String post(String url, String postData, String proxyUrl, Integer proxyPort) {
        String resultJson = "";
        try {
            OkHttpClient client = getUnsafeOkHttpClient(proxyUrl, proxyPort);

            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/x-www-form-urlencoded"), postData);

            Request request = new Request.Builder().url(url)
                    .post(body).build();


            Response response = client.newCall(request).execute();
            resultJson = response.body().string();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
        return resultJson;
    }

    public static String post(String url, java.io.File file, String contentType) {
        return post(url, file, contentType,null, null);
    }

    public static String post(String url, java.io.File file, String contentType, String proxyUrl, Integer proxyPort) {
        String resultJson = "";
        try {

            OkHttpClient client = getUnsafeOkHttpClient(proxyUrl, proxyPort);
            MediaType MEDIA_TYPE = MediaType.parse(contentType);//"image/png"
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    //.addFormDataPart("title", "Square Logo")
                    .addFormDataPart("imageFile", file.getName(),
                            RequestBody.create(file, MEDIA_TYPE))
                    .build();

            Request request = new Request.Builder()
                    //.header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            resultJson = response.body().string();

        }catch (IOException e) {
            System.out.println(e);
            return null;
        }
        return resultJson;
    }

    public static String get(String url) {
        return get(url, null, null);
    }

    public static String get(String url, String proxyUrl, Integer proxyPort) {
        OkHttpClient client = getUnsafeOkHttpClient(proxyUrl, proxyPort);

        Request request = new Request.Builder().url(url).get().build();
        String resultJson = "";
        try {
            Response response = client.newCall(request).execute();
            resultJson = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultJson;
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClientBuilder() {
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(final java.security.cert.X509Certificate[] chain,
                                                   final String authType)
                            throws CertificateException {
                        // Empty Method
                    }

                    @Override
                    public void checkServerTrusted(final java.security.cert.X509Certificate[] chain,
                                                   final String authType)
                            throws CertificateException {
                        // Empty method
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        try {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(50000, TimeUnit.MILLISECONDS)
                    .readTimeout(50000, TimeUnit.MILLISECONDS);
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            });

            return builder;
        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        return getUnsafeOkHttpClient(null, null);
    }

    private static OkHttpClient getUnsafeOkHttpClient(String proxyUrl, Integer proxyPort) {
        OkHttpClient.Builder builder = null;
        if (StringUtils.isNotEmpty(proxyUrl) && proxyPort != null) {
            builder = getUnsafeOkHttpClientBuilder()
                    .readTimeout(50000, TimeUnit.MILLISECONDS)
                    .connectTimeout(50000, TimeUnit.MILLISECONDS)
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl, proxyPort)))
            ;
        } else {
            builder = getUnsafeOkHttpClientBuilder();
        }

        return builder.build();
    }
}