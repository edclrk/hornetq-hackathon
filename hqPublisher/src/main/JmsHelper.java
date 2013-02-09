package main;

import javax.naming.InitialContext;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: clarkee
 * Date: 2/9/13
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class JmsHelper {
    protected static InitialContext getContext(final String  serverId) throws Exception
    {
        System.out.println("using " + serverId + " for jndi");
//        HornetQExample.log.info("using " + args[serverId] + " for jndi");
        Properties props = new Properties();
        props.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
        props.put("java.naming.provider.url", serverId);
        props.put("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");
        return new InitialContext(props);
    }

}
