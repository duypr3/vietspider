package org.apache.jcs;

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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Description of the Class
 *
 */
public class JCSLightLoadUnitTest
    extends TestCase
{

    private static int items = 20000;

    /**
     * Test setup
     */
    public void setUp()
        throws Exception
    {
        JCS.setConfigFilename( "/TestSimpleLoad.ccf" );
        JCS.getInstance( "testCache1" );
    }

    /**
     * Constructor for the TestSimpleLoad object
     *
     * @param testName
     *            Description of the Parameter
     */
    public JCSLightLoadUnitTest( String testName )
    {
        super( testName );
    }

    /**
     * Description of the Method
     *
     * @param args
     *            Description of the Parameter
     */
    public static void main( String args[] )
    {
        String[] testCaseName = { JCSLightLoadUnitTest.class.getName() };
        junit.textui.TestRunner.main( testCaseName );
    }

    /**
     * A unit test suite for JUnit
     *
     * @return The test suite
     */
    public static Test suite()
    {
        return new TestSuite( JCSLightLoadUnitTest.class );
    }

    /**
     * A unit test for JUnit
     *
     * @exception Exception
     *                Description of the Exception
     */
    public void testSimpleLoad()
        throws Exception
    {
        JCS jcs = JCS.getInstance( "testCache1" );
        //        ICompositeCacheAttributes cattr = jcs.getCacheAttributes();
        //        cattr.setMaxObjects( 20002 );
        //        jcs.setCacheAttributes( cattr );

        for ( int i = 1; i <= items; i++ )
        {
            jcs.put( i + ":key", "data" + i );
        }

        for ( int i = items; i > 0; i-- )
        {
            String res = (String) jcs.get( i + ":key" );
            if ( res == null )
            {
                assertNotNull( "[" + i + ":key] should not be null", res );
            }
        }

        // test removal
        jcs.remove( "300:key" );
        assertNull( jcs.get( "300:key" ) );

    }

}
