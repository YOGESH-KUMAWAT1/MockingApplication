package agent;

import java.util.concurrent.Callable;

import net.bytebuddy.asm.Advice.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class JpaRepositoryInterceptor {
    private final String mode;

    public JpaRepositoryInterceptor(String mode) {
        this.mode = mode;
    }

    @RuntimeType
    public Object intercept(@AllArguments Object[] allArguments, @SuperCall Callable<?> zuper) throws Exception {
        if ("REPLAY".equals(System.getenv(mode))) {
            Post post = (Post) allArguments[0];
            post.setId(1L);
            return post;
        }
        Object result = zuper.call();
        System.out.println("DB Save: " + result);
        return result;
    }
}
