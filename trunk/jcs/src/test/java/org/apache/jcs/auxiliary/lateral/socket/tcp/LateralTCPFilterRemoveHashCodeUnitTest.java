package org.apache.jcs.auxiliary.lateral.socket.tcp;

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

import java.io.Serializable;

import junit.framework.TestCase;

import org.apache.jcs.JCS;
import org.apache.jcs.engine.CacheElement;
import org.apache.jcs.engine.behavior.ICacheElement;

/**
 * @author asmuts
 */
public class LateralTCPFilterRemoveHashCodeUnitTest
    extends TestCase
{

    //private static boolean isSysOut = false;

    private static boolean isSysOut = true;

    /**
     * Constructor for the TestDiskCache object.
     *
     * @param testName
     */
    public LateralTCPFilterRemoveHashCodeUnitTest( String testName )
    {
        super( testName );
    }

    /**
     * Test setup
     */
    public void setUp()
    {
        JCS.setConfigFilename( "/TestTCPLateralRemoveFilter.ccf" );
    }

    /**
     *
     * @throws Exception
     */
    public void test()
        throws Exception
    {
        this.runTestForRegion( "region1", 200, 1 );
    }

    /**
     * This tests issues tons of puts. It also check to see that a key that was
     * put in was removed by the clients remove command.
     *
     * @param region
     *            Name of the region to access
     * @param numOps
     * @param testNum
     *
     * @exception Exception
     *                If an error occurs
     */
    public void runTestForRegion( String region, int numOps, int testNum )
        throws Exception
    {

        //boolean show = true;//false;

        JCS cache = JCS.getInstance( region );

        Thread.sleep( 100 );

        TCPLateralCacheAttributes lattr2 = new TCPLateralCacheAttributes();
        lattr2.setTcpListenerPort( 1102 );
        lattr2.setTransmissionTypeName( "TCP" );
        lattr2.setTcpServer( "localhost:1110" );
        lattr2.setIssueRemoveOnPut( true );
        // should still try to remove
        lattr2.setAllowPut( false );

        // this service will put and remove using the lateral to
        // the cache instance above
        // the cache thinks it is different since the listenerid is different
        LateralTCPService service = new LateralTCPService( lattr2 );
        service.setListenerId( 123456 );

        String keyToBeRemovedOnPut = "test1";

        String keyToNotBeRemovedOnPut = "test2";

        Serializable dataToPassHashCodeCompare = new Serializable()
        {
            private static final long serialVersionUID = 1L;

            public int hashCode()
            {
                return 1;
            }
        };
        //String dataToPassHashCodeCompare = "this should be the same and not
        // get removed.";
        //p( "dataToPassHashCodeCompare hashcode = " + +
        // dataToPassHashCodeCompare.hashCode() );

        cache.put( keyToBeRemovedOnPut, "this should get removed." );
        ICacheElement element1 = new CacheElement( region, keyToBeRemovedOnPut, region
            + ":data-this shouldn't get there" );
        service.update( element1 );

        cache.put( keyToNotBeRemovedOnPut, dataToPassHashCodeCompare );
        ICacheElement element2 = new CacheElement( region, keyToNotBeRemovedOnPut, dataToPassHashCodeCompare );
        service.update( element2 );

        /*
         * try { for ( int i = 1; i < numOps; i++ ) { Random ran = new Random( i );
         * int n = ran.nextInt( 4 ); int kn = ran.nextInt( range ); String key =
         * "key" + kn;
         *
         * ICacheElement element = new CacheElement( region, key, region +
         * ":data" + i + " junk asdfffffffadfasdfasf " + kn + ":" + n );
         * service.update( element ); if ( show ) { p( "put " + key ); }
         *
         * if ( i % 100 == 0 ) { System.out.println( cache.getStats() ); }
         *  } p( "Finished cycle of " + numOps ); } catch ( Exception e ) { p(
         * e.toString() ); e.printStackTrace( System.out ); throw e; }
         */

        JCS jcs = JCS.getInstance( region );
        String key = "testKey" + testNum;
        String data = "testData" + testNum;
        jcs.put( key, data );
        String value = (String) jcs.get( key );
        assertEquals( "Couldn't put normally.", data, value );

        // make sure the items we can find are in the correct region.
        for ( int i = 1; i < numOps; i++ )
        {
            String keyL = "key" + i;
            String dataL = (String) jcs.get( keyL );
            if ( dataL != null )
            {
                assertTrue( "Incorrect region detected.", dataL.startsWith( region ) );
            }

        }

        Thread.sleep( 200 );

        Object testObj1 = cache.get( keyToBeRemovedOnPut );
        p( "test object1 = " + testObj1 );
        assertNull( "The test object should have been remvoed by a put.", testObj1 );

        Object testObj2 = cache.get( keyToNotBeRemovedOnPut );
        p( "test object2 = " + testObj2 + " hashCode = " );
        if ( testObj2 != null )
        {
            p( "test2 hashcode = " + +testObj2.hashCode() );
        }
        assertNotNull( "This should not have been removed, since the hascode were the same.", testObj2 );

    }

    /**
     * @param s String to print
     */
    public static void p( String s )
    {
        if ( isSysOut )
        {
            System.out.println( s );
        }
    }
}
