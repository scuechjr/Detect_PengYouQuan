package com.example.swee1.pengyouquan.util;

import com.alibaba.fastjson.JSON;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpUtils {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static String get(final String url) {
        return get(url, true);
    }

    public static String get(final String url, final boolean sync) {
        final StringBuilder result = new StringBuilder();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                HttpURLConnection connection = null;
//                try {
//                    URL httUrl = new URL(url);
//                    connection = (HttpURLConnection) httUrl.openConnection();
//
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(3000); // 设置连接超时时间
//                    connection.setDoInput(true); // 设置开启输入
//                    connection.setDoOutput(true); // 设置开启输出
//                    connection.setUseCaches(false); // 使用post不能使用缓存
//                    connection.setRequestProperty("Content-Type", "application/json"); // api要求
//
//                    StringBuilder sb = new StringBuilder();
//                    InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
//                    BufferedReader bufferedReader = new BufferedReader(inputStream);
//                    for (;;) {
//                        String line = bufferedReader.readLine();
//                        if (null == line) {
//                            break;
//                        }
//                        sb.append(line);
//                    }
//                    result.append(sb.toString());
//                } catch (Exception e) {
//                    Log.i("error", "http get error, msg: " + e.getMessage());
//                } finally {
//                    // 关闭链接
//                    if (connection != null) {
//                        connection.disconnect();
//                    }
//                }
                try {
                    RestTemplate restTemplate = new RestTemplate();

                    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                    result.append(restTemplate.getForObject(url, String.class));
                } catch (Exception e) {
                    Log.i("error", "http get error, msg: " + e.getMessage());
                }
            }
        };

        if (sync) {
            Future future = executorService.submit(runnable);
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            executorService.execute(runnable);
        }
        return result.toString();
    }

    public static String post(final String url, final Map<String, Object> params, final boolean sync) {
        return post(url, JSON.toJSONBytes(params), sync);
    }

    public static String post(final String url, final Map<String, Object> params) {
        return post(url, JSON.toJSONBytes(params), true);
    }

    public static String post(final String url, final String body) {
        return post(url, body, true);
    }

    public static String post(final String url, final String body, final boolean sync) {
        return post(url, body.getBytes(), sync);
    }

    public static String post(final String url, final byte[] body, final boolean sync) {
        final StringBuilder result = new StringBuilder();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL httUrl = new URL(url);
                    conn = (HttpURLConnection) httUrl.openConnection();
                    conn.setConnectTimeout(3000); // 设置连接超时时间
                    conn.setDoInput(true); // 设置开启输入
                    conn.setDoOutput(true); // 设置开启输出
                    conn.setRequestMethod("POST");
                    conn.setUseCaches(false); // 使用post不能使用缓存
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Content-Length", String.valueOf(body.length)); // 数据长度
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(body); // 输出流发送到服务器

                    InputStreamReader inputStream = new InputStreamReader(conn.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStream);
                    for (;;) {
                        String line = bufferedReader.readLine();
                        if (null == line) {
                            break;
                        }
                        result.append(line); // 返回数据拼接
                    }
                } catch (IOException e) {
                    Log.i("error", "http post error, msg: " + e.getMessage());
                } finally {
                    if (conn != null) {
                        conn.disconnect(); // 关闭链接
                    }
                }

//                try {
//                    RestTemplate restTemplate = new RestTemplate();
//                    restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
//                    result.append(restTemplate.postForEntity(url, body, String.class).getBody());
//                } catch (Exception e) {
//                    Log.i("error", "http get error, msg: " + e.getMessage());
//                }
            }
        };

        if (sync) {
            Future future = executorService.submit(runnable);
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            executorService.execute(runnable);
        }
        return result.toString();
    }
}
