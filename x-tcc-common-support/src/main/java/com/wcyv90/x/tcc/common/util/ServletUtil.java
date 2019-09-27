package com.wcyv90.x.tcc.common.util;

import com.wcyv90.x.tcc.common.exception.AppException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ServletUtil {

    public static HttpServletRequest getRequest() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .orElseThrow(AppException::new);
    }

}
