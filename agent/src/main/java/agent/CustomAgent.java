package agent;
	
import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

	public class CustomAgent {
	    
		public static void premain(String agentArgs, Instrumentation inst) {
	        String mode = System.getProperty("HT_MODE", "RECORD");

	        new AgentBuilder.Default()
	                .type(ElementMatchers.nameContains("org.springframework.web.client.RestTemplate"))
	                .transform((builder, type, classLoader, module) -> 
	                    builder.method(ElementMatchers.named("doExecute"))
	                           .intercept(MethodDelegation.to(new RestTemplateInterceptor(mode))))
	                .installOn(inst);

	        new AgentBuilder.Default()
	                .type(ElementMatchers.nameContains("org.springframework.data.jpa.repository.support.SimpleJpaRepository"))
	                .transform((builder, type, classLoader, module) -> 
	                    builder.method(ElementMatchers.named("save"))
	                           .intercept(MethodDelegation.to(new JpaRepositoryInterceptor(mode))))
	                .installOn(inst);
	    }
	}
