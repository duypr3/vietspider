package org.apache.jcs.auxiliary.lateral.xmlrpc;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.jcs.auxiliary.lateral.behavior.ILateralCacheAttributes;
import org.apache.jcs.auxiliary.lateral.behavior.ILateralCacheListener;
import org.apache.jcs.auxiliary.lateral.xmlrpc.behavior.ILateralCacheXMLRPCListener;

import org.apache.jcs.engine.control.CompositeCacheManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @version $Id: LateralGroupCacheXMLRPCListener.java,v 1.6 2002/02/15 04:33:37
 *      jtaylor Exp $
 */
public class LateralGroupCacheXMLRPCListener
     extends LateralCacheXMLRPCListener
     implements ILateralCacheXMLRPCListener
{
    private final static Log log =
        LogFactory.getLog( LateralGroupCacheXMLRPCListener.class );

    /**
     * Constructor for the LateralGroupCacheXMLRPCListener object
     *
     * @param ilca
     */
    protected LateralGroupCacheXMLRPCListener( ILateralCacheAttributes ilca )
    {
        super( ilca );
        log.debug( "creating LateralGroupCacheXMLRPCListener" );
    }


    /**
     * Gets the instance attribute of the LateralGroupCacheXMLRPCListener class
     *
     * @return The instance value
     */
    public static ILateralCacheListener getInstance( ILateralCacheAttributes ilca )
    {
        //throws IOException, NotBoundException
        ILateralCacheListener ins = ( ILateralCacheListener ) instances.get( String.valueOf( ilca.getTcpListenerPort() ) );
        if ( ins == null )
        {
            synchronized ( LateralGroupCacheXMLRPCListener.class )
            {
                if ( ins == null )
                {
                    ins = new LateralGroupCacheXMLRPCListener( ilca );
                    ins.init();
                }
                if ( log.isDebugEnabled() )
                {
                    log.debug( "created new listener " + ilca.getTcpListenerPort() );
                }
                instances.put( String.valueOf( ilca.getTcpListenerPort() ), ins );
            }
        }
        return ins;
    }


    // override for new funcitonality
    // lazy init is too slow, find a better way
    /**
     * Gets the cacheManager attribute of the LateralGroupCacheXMLRPCListener
     * object
     */
    protected void getCacheManager()
    {
        try
        {
            if ( cacheMgr == null )
            {
                cacheMgr = CompositeCacheManager.getInstance();
                if ( log.isDebugEnabled() )
                {
                    log.debug( " groupcache cacheMgr = " + cacheMgr );
                }
            }
            else
            {
                if ( log.isDebugEnabled() )
                {
                    log.debug( "already got groupcache cacheMgr = " + cacheMgr );
                }
            }
        }
        catch ( Exception e )
        {
            log.error( e );
        }
    }

}
