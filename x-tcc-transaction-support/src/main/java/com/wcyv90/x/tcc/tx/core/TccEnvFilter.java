package com.wcyv90.x.tcc.tx.core;


import com.wcyv90.x.tcc.common.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TccEnvFilter implements Filter {

    public static final int TCC_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE + 100;

    public static final String TCC_HEADER = "X-tcc-context";

    private static final Logger LOGGER = LoggerFactory.getLogger(TccEnvFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        TccContext tccContext = JsonMapper.load(httpServletRequest.getHeader(TCC_HEADER), TccContext.class);
        if (tccContext != null) {
            LOGGER.debug("TccContext found: {tccTxId: {}, phase: {}}", tccContext.getTccTxId(), tccContext.getPhase());
            TccTransactionManager.setContext(tccContext);
        }
        filterChain.doFilter(servletRequest, servletResponse);
        TccTransactionManager.clearContext();
    }

}