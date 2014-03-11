package org.apache.jcs.utils.access;

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

import junit.framework.TestCase;

/**
 * Test cases for the JCS worker.
 *
 * @author Aaron Smuts
 *
 */
public class JCSWorkerUnitTest
    extends TestCase
{

    /**
     * Test basic worker funtionality.  This is a serial not a concurrent test.
     * <p>
     * Just verify that the worker will go to the cache before asking the helper.
     *
     * @throws Exception
     *
     */
    public void testSimpleGet()
        throws Exception
    {
        JCSWorker cachingWorker = new JCSWorker( "example region" );

        // This is the helper.
        JCSWorkerHelper helper = new AbstractJCSWorkerHelper()
        {
            int timesCalled = 0;

            public Object doWork()
            {
                Object results = new Long( ++timesCalled );
                return results;
            }
        };

        String key = "abc";

        Long result = (Long) cachingWorker.getResult( key, helper );
        assertEquals( "Called the wrong number of times", new Long( 1 ), result );

        // should get it fromthe cache.
        Long result2 = (Long) cachingWorker.getResult( key, helper );
        assertEquals( "Called the wrong number of times", new Long( 1 ), result2 );

    }

}
