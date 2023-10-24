package com.company.libmanagementgateway.filter;

import com.company.libmanagementutils.utils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p></p>
 * 自定义一个GlobalFilter类
 *
 * @date 2023/10/23
 * @auther loda
 */
@Component
public class CustomFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        if (request.getURI().getRawPath().contains("/api/v1/auth")){
            return chain.filter(exchange);
        }
        List<String> tokenList = exchange.getRequest().getHeaders().get("token");

        if (tokenList == null|| tokenList.size() == 0){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE); //这个状态码是406

            return exchange.getResponse().setComplete();
        }

        if(JwtUtils.getUserIdInToken(tokenList.get(0)) == null){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE); //这个状态码是406

            return exchange.getResponse().setComplete();
        }

        boolean isAdmin = JwtUtils.getRoleIdInToken(tokenList.get(0)).equals("Admin")? true: false;
        if (!isAdmin && (request.getURI().getRawPath().contains("new-book") ||
                (request.getURI().getRawPath().contains("book/book")) && request.getMethodValue().equals("POST"))){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE); //这个状态码是406

            return exchange.getResponse().setComplete();
        }

        //放行
        return chain.filter(exchange);
    }

    /**
     *
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}