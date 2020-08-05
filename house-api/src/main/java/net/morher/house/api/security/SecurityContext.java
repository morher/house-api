package net.morher.house.api.security;

import javax.net.ssl.SSLContext;

/**
 * The node security context.
 * 
 * @author Morten Hermansen
 */
public interface SecurityContext {

    /**
     * Retrieve the {@link SSLContext} for the node.
     * 
     * @return The {@link SSLContext}
     */
    SSLContext getSSLContext();
}
