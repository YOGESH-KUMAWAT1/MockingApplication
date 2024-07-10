package agent;

import java.util.concurrent.Callable;

import net.bytebuddy.asm.Advice.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class RestTemplateInterceptor {
    private final String mode;

    public RestTemplateInterceptor(String mode) {
        this.mode = mode;
    }

    @RuntimeType
    public Object intercept(@AllArguments Object[] allArguments, @SuperCall Callable<?> zuper) throws Exception {
        if ("REPLAY".equals(System.getenv(mode))) {
            return "{\"timezone\":\"Asia/Kolkata\",\"datetime\":\"2023-07-03T12:34:56.789Z\"}";
        }
        Object result = zuper.call();
        System.out.println("HTTP Response: " + result);
        return result;
    }
}
