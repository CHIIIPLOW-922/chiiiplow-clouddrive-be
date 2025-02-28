package com.chiiiplow.clouddrive.component;

import com.chiiiplow.clouddrive.dto.UserInfoDTO;
import com.chiiiplow.clouddrive.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义 Web 客户端
 *
 * @author yangzhixiong
 * @date 2025/02/27
 */
@Service
public class CustomWebClient {

    @Value("${siliconflow.token}")
    private String token;

    @Value("${siliconflow.baseUrl}")
    private String baseUrl;

    private final WebClient.Builder webClientBuilder;

    public CustomWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    private static final String CHAT_COMPLETIONS = "/chat/completions";

    private static final String AUDIO_SPEECH = "/audio/speech";

    public Flux<String> generateText() {
        Map<String, Object> body = new HashMap<>();
        body.put("model", "deepseek-ai/DeepSeek-R1");
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(new HashMap<String, String>() {{
            put("role", "user");
            put("content", "测试");
        }});
        body.put("messages", messages);
        return webClientBuilder.baseUrl(baseUrl)
                .build()
                .post() // 使用GET请求
                .uri(CHAT_COMPLETIONS)
                .header("Authorization", "Bearer" + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(String.class).map(item -> {
                    System.out.println(item);
                    return item;
                });
    }


}
