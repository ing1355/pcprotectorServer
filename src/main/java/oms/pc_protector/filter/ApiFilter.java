package oms.pc_protector.filter;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import oms.pc_protector.restApi.log.model.LogVO;
import oms.pc_protector.restApi.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Log4j2
@Component
@WebFilter(urlPatterns = {"/v1/*"}, description = "API LOG FILTER")
public class ApiFilter implements Filter {

    private LogService logService;

    public ApiFilter(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Map<String, String[]> parameterMap = request.getParameterMap();

        LogVO logVO = new LogVO();
        logVO.setUri(request.getRequestURI());
        logVO.setMethod(request.getMethod());
        logVO.setIpAddress(request.getRemoteAddr());

        boolean hasClientURI = excludeURI(request);

        if (hasClientURI) {
            log.info("=============CLIENT API=============");
        }

        else {
            log.info("============FRONTEND API============");
            logService.register(logVO);
        }
        log.info("Request Uri: {}", request.getRequestURI());

        Set<String> ketSet = parameterMap.keySet();

        for (String parameterKey : ketSet) {
            String[] parameterValueArray = parameterMap.get(parameterKey);
            log.info("ParameterKey : " + parameterKey + " , ParameterValue : " + Arrays.toString(parameterValueArray));
        }

        log.info("Request Method: {}", request.getMethod());
        log.info("Request IpAddress : {}", request.getRemoteAddr());

        filterChain.doFilter(request, response);

        log.info("====================================");
        log.info("");


    }

    @Override
    public void destroy() {

    }

    // 클라이언트 API 구분하기 위한 메소드
    private boolean excludeURI(HttpServletRequest request) {
        String uri = request.getRequestURI().toString().replaceAll("v1/", "");
        return uri.startsWith("/client/");
    }
}
