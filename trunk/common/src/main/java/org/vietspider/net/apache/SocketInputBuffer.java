/*
 * $HeadURL: https://svn.apache.org/repos/asf/httpcomponents/httpcore/tags/4.0.1/httpcore/src/main/java/org/apache/http/impl/io/SocketInputBuffer.java $
 * $Revision: 744526 $
 * $Date: 2009-02-14 18:04:18 +0100 (Sat, 14 Feb 2009) $
 *
 * ====================================================================
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
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.vietspider.net.apache;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;

import org.apache.http.io.EofSensor;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

/**
 * {@link SessionInputBuffer} implementation bound to a {@link Socket}.
 *
 *
 * @version $Revision: 744526 $
 * 
 * @since 4.0
 */
public class SocketInputBuffer extends AbstractSessionInputBuffer implements EofSensor {

  static private final Class SOCKET_TIMEOUT_CLASS = SocketTimeoutExceptionClass();

  /**
   * Returns <code>SocketTimeoutExceptionClass<code> or <code>null</code> if the class
   * does not exist.
   * 
   * @return <code>SocketTimeoutExceptionClass<code>, or <code>null</code> if unavailable.
   */ 
  static private Class SocketTimeoutExceptionClass() {
    try {
      return Class.forName("java.net.SocketTimeoutException");
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  private static boolean isSocketTimeoutException(final InterruptedIOException e) {
    if (SOCKET_TIMEOUT_CLASS != null) {
      return SOCKET_TIMEOUT_CLASS.isInstance(e);
    } else {
      return true;
    }
  }

  private final Socket socket;

  private boolean eof;

  /**
   * Creates an instance of this class. 
   * <p>
   * The following HTTP parameters affect the initialization:
   * <p>
   * The {@link CoreProtocolPNames#HTTP_ELEMENT_CHARSET}
   * parameter determines the charset to be used for decoding HTTP lines. If 
   * not specified, <code>US-ASCII</code> will be used per default.
   * <p>
   * The {@link CoreConnectionPNames#MAX_LINE_LENGTH} parameter determines 
   * the maximum line length limit. If set to a positive value, any HTTP 
   * line exceeding this limit will cause an IOException. A negative or zero 
   * value will effectively disable the check. Per default the line length 
   * check is disabled.
   *    
   * @param socket the socket to read data from. 
   * @param buffersize the size of the internal buffer. If this number is less
   *   than <code>0</code> it is set to the value of 
   *   {@link Socket#getReceiveBufferSize()}. If resultant number is less 
   *   than <code>1024</code> it is set to <code>1024</code>. 
   * @param params HTTP parameters.
   * 
   * @see CoreProtocolPNames#HTTP_ELEMENT_CHARSET
   * @see CoreConnectionPNames#MAX_LINE_LENGTH
   */
  public SocketInputBuffer(
      final Socket socket, 
      int buffersize, 
      final HttpParams params) throws IOException {
    super();
    if (socket == null) {
      throw new IllegalArgumentException("Socket may not be null");
    }
    this.socket = socket;
    this.eof = false;
    if (buffersize < 0) {
      buffersize = socket.getReceiveBufferSize();
    }
    if (buffersize < 1024) {
      buffersize = 1024;
    }
    init(socket.getInputStream(), buffersize, params);
  }

  protected int fillBuffer() throws IOException {
    int i = super.fillBuffer();
    this.eof = i == -1;
    return i;
  }

  public boolean isDataAvailable(int timeout) throws IOException {
    boolean result = hasBufferedData();
    if (!result) {
      int oldtimeout = this.socket.getSoTimeout();
      try {
        this.socket.setSoTimeout(timeout);
        fillBuffer();
        result = hasBufferedData();
      } catch (InterruptedIOException e) {
        if (!isSocketTimeoutException(e)) {
          throw e;
        }
      } finally {
        socket.setSoTimeout(oldtimeout);
      }
    }
    return result;
  }

  public boolean isEof() {
    return this.eof;
  }    

}
