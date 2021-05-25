package pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors;

import lombok.extern.java.Log;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log
public class TrackingInterceptor {
    @Resource
    private SessionContext context;

    @AroundInvoke
    public Object logActions(InvocationContext context) throws Exception {

        String resultString;
        List<Object> paramList = context.getParameters() == null ? new ArrayList<>() : Arrays.asList(context.getParameters());
        try {
            Object result = context.proceed();
            resultString = result == null ? "without return value" : "with returned value \"" + result + "\"";
            log.info(getLogString(context.getMethod().toString(), paramList, resultString));
            return result;
        } catch (Exception e) {
            resultString = String.format("with exception thrown: \"%s\", \ncause: \"%s\", \nmessage: \"%s\"", e, e.getCause(), e.getMessage());
            log.severe(getLogString(context.getMethod().toString(), paramList, resultString));
            throw e;
        }
    }

    private String getLogString(String methodName, Iterable<Object> parameters, String resultString) {
        return String.format("Method \"%s\" " +
                "\ncalled by user \"%s\" " +
                "\nwith arguments \"%s\", " +
                "\n%s", methodName, context.getCallerPrincipal().getName(), parameters, resultString);
    }
}
