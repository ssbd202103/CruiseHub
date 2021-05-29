package pl.lodz.p.it.ssbd2021.ssbd03.utils.interceptors;

import lombok.extern.java.Log;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private String getLogString(String methodName, List<Object> parameters, String resultString) {
        List<Object> paramsStrings = parameters.stream()
                .map(param -> {
                    if (param instanceof BaseEntity) {
                        BaseEntity entity = (BaseEntity) param;
                        return String.format("Entity: %s, \nidentifier: %s, version: %s\n", entity, entity.getIdentifier(), entity.getVersion());
                    }
                    return param;
                }).collect(Collectors.toList());

        return String.format("Method \"%s\" " +
                "\ncalled by user \"%s\" " +
                "\nwith parameters: \n%s, " +
                "\n%s", methodName, context.getCallerPrincipal().getName(), paramsStrings, resultString);
    }
}
