/***************************************************************************
 * Copyright 2001-2009 The VietSpider         All rights reserved.  		 *
 **************************************************************************/
package org.vietspider.gui.log.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.vietspider.chars.refs.RefsEncoder;
import org.vietspider.client.common.ClientConnector2;
import org.vietspider.client.common.ZipRatioWorker;
import org.vietspider.common.Application;
import org.vietspider.common.io.GZipIO;
import org.vietspider.net.client.AbstClientConnector.HttpData;
import org.vietspider.net.server.URLPath;
import org.vietspider.ui.services.ClientLog;

/** 
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * Oct 17, 2009  
 */
public class LoadLogHandler {

  private String logFolder;
  private String name;
  private boolean html = false;

  LoadLogHandler(String logFolder, String name, boolean html)  {
    this.logFolder = logFolder;
    this.name = name;
    this.html = html;
  }

  void execute(ZipRatioWorker worker, String pattern, boolean update) throws Exception {
    //    String curDate = CommonDateTimeFormat.getFolderFormat().format(new Date());
    String [] patterns  = pattern.trim().split("&");
    for(int i = 0; i < patterns.length; i++) {
      patterns[i] = patterns[i].trim();
    }

    long bytesCounter = 0;
    int page = 1;

    InputStreamReader inputReader = null;
    BufferedReader bufferedReader = null;
    BufferedWriter bufferedWriter = null;

    FileOutputStream fileWriter = null;
    ClientConnector2 connector = ClientConnector2.currentInstance();
    HttpData httpData = null;
    //    System.out.println("  ========  >" + logFolder + name + ".log" );
    try {
      Header [] headers =  new Header[] {
          new BasicHeader("action", "load.file.by.gzip"),
          new BasicHeader("file", logFolder + name + ".log")
      };
      //                System.out.println( "Load file:>>>>track/logs/"+log+ " // "+type);
      httpData = connector.loadResponse(URLPath.FILE_HANDLER, new byte[0], headers);
      Header header = httpData.getResponseHeader("Content-Length");
      worker.setTotal(Integer.parseInt(header.getValue()));

      File fileTemp = getCachedFile();
      if(update  || !fileTemp.exists() || fileTemp.length()  < 1) {
        if(fileTemp.exists()) fileTemp.delete();
        FileOutputStream fileOuput =  new FileOutputStream(fileTemp);

        InputStream contentInput = httpData.getStream();
        try {
          new GZipIO().load(contentInput, fileOuput, worker);
        } finally {
          connector.release(httpData);
        }
      } 

      inputReader = new InputStreamReader(new FileInputStream(fileTemp), Application.CHARSET);
      bufferedReader = new BufferedReader(inputReader);
      String line = null;
      boolean isEmtpy = false;
      worker.setRatio(0);
      RefsEncoder encoder = new RefsEncoder();
      while((line = bufferedReader.readLine()) != null) {
        worker.increaseRatio(line.length());
        isEmtpy = line.trim().isEmpty();
        if(isEmtpy) {
          if(bytesCounter < 1) continue;
        } else {
          if(!isIndexOfPatterns(patterns, line)) continue;
        }

        if(bytesCounter < 1) {
          File f = getCachedFile(page);
          if(fileWriter != null) fileWriter.close();

          fileWriter = new FileOutputStream(f);
          OutputStreamWriter outputWriter = new OutputStreamWriter(fileWriter, Application.CHARSET);
          bufferedWriter = new BufferedWriter(outputWriter);
          page++;
        }

        //        bufferedWriter.append("<span style=\"font-family: Arial;font-size: 12px;\">");
        if(html) {
          bufferedWriter.append(new String(encoder.encode(line.toCharArray())));
        } else {
          bufferedWriter.append(line);
        }
        //        bufferedWriter.append("</span>");
        if(isEmtpy) {
          if(html) {
            bufferedWriter.append("<p></p><br/>\n"); 
          } else {
            bufferedWriter.append("\n"); 
          }
        } else {
          if(html) {
            bufferedWriter.append("<br/>\n");
          } else {
            bufferedWriter.append("\n"); 
          }
        }
        bytesCounter += line.trim().length();
        bufferedWriter.flush();
        if(bytesCounter >= 20*1024) bytesCounter = 0;
      }
      if(fileWriter != null) fileWriter.close();
//      System.out.println("file temp "+ fileTemp.getAbsolutePath() + " : "+ fileTemp.exists());
    } finally {
      connector.release(httpData);
      try {
        if(fileWriter != null) fileWriter.close();
      }catch (Exception e) {
        ClientLog.getInstance().setException(null, e);
      }

      try {
        if(inputReader != null) inputReader.close();
      }catch (Exception e) {
        ClientLog.getInstance().setException(null, e);
      }

      try {
        if(bufferedReader != null) bufferedReader.close();
      }catch (Exception e) {
        ClientLog.getInstance().setException(null, e);
      }
    }
    
  }

  private boolean isIndexOfPatterns(String [] patterns, String line) {
    if(patterns == null) return true;
    for(String pattern : patterns) {
      if(pattern.length() > 0 && line.indexOf(pattern) < 0) return false; 
    }
    return true;
  }

  //  private Header[] getHeaders() throws Exception {
  //    //    Date instanceDate = CalendarUtils.getDateFormat().parse(date);
  //    //    date = CalendarUtils.getFolderFormat().format(instanceDate);
  //    //    DataClientHandler dataClient  = new DataClientHandler();
  //    //    String [] logs = dataClient.listLogs(date);
  //    if(type == 1) {
  //      SimpleDateFormat dateFormat = CalendarUtils.getDateFormat();
  //      Date dateInstance = dateFormat.parse(date);
  //      String dateFolder = CalendarUtils.getFolderFormat().format(dateInstance);
  //
  //      return new Header[] {
  //          new BasicHeader("action", "load.file"),
  //          new BasicHeader("file", "track/logs/"+dateFolder+".log")
  //      };
  //    } else if(type == 2) {
  //      if(sourceDate == null || sourceName == null)  {
  //        throw new InvalidParameterException("source null or date null");
  //      }
  //      SimpleDateFormat dateFormat = CalendarUtils.getDateFormat();
  //      Date dateInstance = dateFormat.parse(sourceDate);
  //      String dateFolder = CalendarUtils.getFolderFormat().format(dateInstance);
  //
  //      return new Header[] {
  //          new BasicHeader("action", "load.file"),
  //          new BasicHeader("file", "track/logs/sources/"+dateFolder+"/www."+sourceName+".log")
  //      };
  //
  //    }
  //
  //    SimpleDateFormat dateFormat = CalendarUtils.getDateFormat();
  //    Date dateInstance = dateFormat.parse(date);
  //    String dateFolder = CalendarUtils.getFolderFormat().format(dateInstance);
  //    return new Header[] {
  //        new BasicHeader("action", "load.file"),
  //        new BasicHeader("file", "track/logs/website/"+dateFolder+".log")
  //    };
  //  }

  private File getCachedFile() {
    return ClientConnector2.getCacheFile("server/" + logFolder, "log.temp");
  }

  private File getCachedFile(int page) {
    String log = "log"+String.valueOf(page-1);
    return ClientConnector2.getCacheFile("server/" + logFolder, log);
  }

}
