package com.farabbit.threaddemo.client_01;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *   graphql-client
 *
 * @author mengka
 * @Date 2022-03-31 21:06
 */
@Log4j2
public class graplql_01 {

    private static final String CONTENT_TYPE = "application/graphql";

    public static void main(String... args) throws Exception {
        String id = invokeRemoteService("currentPlayerAll",11L);
        log.info("--------, id = "+id);

//        String id2 = invokeRemoteService("currentPlayer",11L);
//        log.info("--------, id = "+id2);
    }

    private static String invokeRemoteService(String methodName, Long traceId) throws Exception {
        String url = "http://127.0.0.1:8080/graphql";// /Users/hyy044101331/work_sanqianyuejia_2022/spring-learn-2022/graphql-server-demo

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(CONTENT_TYPE));// set `content-type` header

        // request body parameters

        // build the request
        String requestBody = null;
        if("currentPlayerAll".equals(methodName)){
            requestBody = readRequestBody();
        }else if("currentPlayer".equals(methodName)){
            requestBody = readRequestBodyById();
        }
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // send POST request
        ResponseEntity<Object> response = restTemplate.postForEntity(url, entity, Object.class);

        // check response
        if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {
            log.info("Request [" + traceId + "] Successful 200 content = \n" + JSON.toJSONString(response.getBody(),true));
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(response.getBody()));
            JSONObject data = jsonObject.getJSONObject("data");
            String ids = "";
            if("currentPlayerAll".equals(methodName)){
                JSONArray currentPlayers = data.getJSONArray(methodName);
                /**获取id列表**/
                List<String> playerIds = Arrays.stream(currentPlayers.toArray()).map(f -> ((JSONObject)f).getString("id")).collect(Collectors.toList());

                String playerIdsStr = playerIds.stream().collect(Collectors.joining("::"));
                ids = playerIdsStr;

            }else if("currentPlayer".equals(methodName)){
                JSONObject currentPlayer = data.getJSONObject(methodName);
                ids = currentPlayer.getString("id");
            }

            return ids;
        } else {
            log.info("Request [" + traceId + "] Failed [" + response.getStatusCode() + "] content = " + response.getBody());
            return "-1";
        }
    }

    /**
     * 》》graphql服务：
     *  https://github.com/nurkiewicz/graphql-server-demo
     *  https://nurkiewicz.com/2019/09/graphql-server-in-java-part-i-basics.html
     *  https://nurkiewicz.com/2020/03/graphql-server-in-java-part-iii.html
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    private static String readRequestBody() throws URISyntaxException, IOException {
        Stream<String> lines = Files.lines(
                Paths.get(ClassLoader.getSystemResource("request_body.txt").toURI()));

        return lines.collect(Collectors.joining());
    }

    /**
     * 》》请求参数id：
     *   https://piotrminkowski.com/2020/07/31/an-advanced-guide-to-graphql-with-spring-boot/
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    private static String readRequestBodyById() throws URISyntaxException, IOException {
        Stream<String> lines = Files.lines(
                Paths.get(ClassLoader.getSystemResource("request_body3.txt").toURI()));

        return lines.collect(Collectors.joining());
    }
}
