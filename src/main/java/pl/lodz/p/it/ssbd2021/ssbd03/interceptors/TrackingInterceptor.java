package pl.lodz.p.it.ssbd2021.ssbd03.interceptors;

//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

//@Slf4j
public class TrackingInterceptor {

    private static final Logger logger = Logger.getLogger(TrackingInterceptor.class.getName());

    @Resource
    private SessionContext context;

    @AroundInvoke
    public Object logActions(InvocationContext context) throws Exception {

        String resultString;
        List<Object> paramList = context.getParameters() == null ? new ArrayList<>() : Arrays.asList(context.getParameters());
        try {
            Object result = context.proceed();
            resultString = result == null ? "without return value" : "with returned value" + result;
            logger.info(getLogString(context.getMethod().getName(), paramList, resultString));
            return result;
        } catch (Exception e) {
            resultString = String.format("with exception thrown: \"%s\", \ncause: \"%s\", \nmessage: \"%s\"", e, e.getCause(), e.getMessage());
            logger.severe(getLogString(context.getMethod().toString(), paramList, resultString));
            throw e;
        }
    }

    private String getLogString(String methodName, Iterable<Object> parameters, String resultString) {
        return String.format("Method \"%s\" " +
                "\ncalled by user \"%s\" " +
                "\nwith arguments \"%s\", " +
                "\n\"%s\"", methodName, context.getCallerPrincipal().getName(), parameters, resultString);
    }
}
