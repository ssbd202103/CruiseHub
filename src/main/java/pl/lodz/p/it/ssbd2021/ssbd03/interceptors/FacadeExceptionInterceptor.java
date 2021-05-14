package pl.lodz.p.it.ssbd2021.ssbd03.interceptors;

import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.BaseAppException;
import pl.lodz.p.it.ssbd2021.ssbd03.exceptions.FacadeException;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.OptimisticLockException;

public class FacadeExceptionInterceptor {
    @AroundInvoke
    public Object intercept(InvocationContext invocationContext) throws Exception {
        try {
            return invocationContext.proceed();
        } catch (OptimisticLockException e) {
            throw FacadeException.optimisticLock(e);
        }
    }
}
