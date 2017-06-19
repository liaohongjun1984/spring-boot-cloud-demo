package com.idohoo.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * SFTP工具类
 *
 * @author tengfei.fangtf
 *
 */
public class SftpHelper {

    private static final int    SFTP_DEFAULT_TIMEOUT = 10000;

    private static final Logger LOGGER               = LoggerFactory.getLogger(SftpHelper.class);

    private String              ip;
    private String              user;
    private String              password;
    private int                 port                 = 1433;
    private String              ftpFilePaths;

    private Session             session              = null;
    private ChannelSftp         channelSftp          = null;

    public SftpHelper(String ip, String user, String password, String ftpFilePath) {
        this.ip = ip;
        this.user = user;
        this.password = password;
        this.ftpFilePaths = ftpFilePath;
    }

    public SftpHelper(String ip, String user, String password, String ftpFilePath, int port) {
        this.ip = ip;
        this.user = user;
        this.password = password;
        this.port = port;
        this.ftpFilePaths = ftpFilePath;
    }

    public void create() {
        session = createSession();
        channelSftp = createChannel(session);

        //进入服务器指定的文件夹
        try {
        	 LOGGER.info("ftpFilePaths="+ftpFilePaths);
            channelSftp.cd(ftpFilePaths);
        } catch (SftpException e) {
            LOGGER.error("channelSftp cd has error,", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传某个路径的文件,28M文件约7秒左右
     *
     * @param filePath 上传文件的路径
     * @param fileName 上传文件名，上传到服务器端也是用这个文件名
     */
    public void put(String filePath, String fileName) {
        //本地文件是否存在
        String localFileName = filePath + fileName;
        if (!new File(localFileName).exists()) {
            LOGGER.error("file not exists,filePath={}，fileName={}", filePath, fileName);
            throw new RuntimeException("file not exists,localFileName=" + localFileName);
        }

        //开始上传
        FileChannel fromChannel = null;
        WritableByteChannel toChannel = null;
        try {
            fromChannel = new FileInputStream(localFileName).getChannel();
            toChannel = Channels.newChannel(channelSftp.put(fileName));
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } catch (Exception e) {
            LOGGER.error("put has error,", e);
            throw new RuntimeException(e);
        } finally {
            close(fromChannel);
            close(toChannel);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void close(BufferedReader localReader, OutputStreamWriter ftpWriter) {
        try {
            localReader.close();
            ftpWriter.close();
        } catch (IOException e) {
            LOGGER.error("close has error,", e);
        }
    }

    /**
     * 获得类路径下的文件
     *
     * @param fileName
     * @return
     */
    public static String getResourcePath(String fileName) {
        URL resource = Class.class.getClass().getResource(fileName);
        return resource == null ? null : resource.getPath();
    }

    public File get(String fileName, String downloadDir) {

        OutputStream outputStream = null;
        InputStream sftpFIleInputStream = null;
        try {
            sftpFIleInputStream = channelSftp.get(fileName);
            //文件不存在直接返回
            if (sftpFIleInputStream == null) {
                return null;
            }
            //创建本地文件
            File file = createLocalFile(downloadDir + fileName);
            outputStream = new FileOutputStream(file);
 
            byte[] buf = new byte[1024];
            int i;

            //这里必须使用字节读取，字符读取遇到二进制文件会有问题
            while (true) {
                i = sftpFIleInputStream.read(buf, 0, 1024);
                outputStream.write(buf);
                if (i <= 0) {
                    break;
                }
            }

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("{} download from sftp servce {} success!", fileName, ip);
            }
            return file;
 
        } catch (Exception e) {
            LOGGER.error("get has error,fileName=" + fileName, e);
            throw new RuntimeException(e);
        } finally {
            close(outputStream);
            close(sftpFIleInputStream);
        }
    } 

    protected File createLocalFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            return file;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            LOGGER.error("createNewFile has error,fileName=" + fileName, e);
        }
        return file;
    }

    public void close() {
        session.disconnect();
        channelSftp.disconnect();
    }

    protected ChannelSftp createChannel(Session session) {
        Channel channel;
        try {
            channel = (Channel) session.openChannel("sftp");
            channel.connect();
            return (ChannelSftp) channel;
        } catch (JSchException e) {
            LOGGER.error("createChannel has error", e);
            throw new RuntimeException(e);
        }
    }

    protected Session createSession() {
        JSch jsch = new JSch();
        Session session;
        try {
            LOGGER.info(user+","+password+","+ip+port);
            session = jsch.getSession(user, ip, port);
            session.setPassword(password);//设置密码
            session.setTimeout(SFTP_DEFAULT_TIMEOUT);
            //设置第一次登陆的时候提示，可选值：(ask | yes | no)
            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("StrictHostKeyChecking", "no");
            session.setConfig(hashtable);
            session.connect();
            return session;
        } catch (JSchException e) {
            LOGGER.error("createSession has error", e);
            throw new RuntimeException(e);
        }
    }

    public static String getIP(String name) {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(name);
        } catch (UnknownHostException e) {
            LOGGER.error("getIP has error", e);
            throw new RuntimeException(e);
        }
        return address.getHostAddress().toString();
    }
   
    
    public static SftpHelper getSftpHelper(String url, String user, String password, String ftpFilePath,int times,long millis){
    	SftpHelper sftpHelper = new SftpHelper(SftpHelper.getIP(url), user, password, ftpFilePath);
    	for(int i = 0; i < times ; i++){
    		try {
        		sftpHelper.create();
        		break;
    		} catch (Exception e) {
    			try {
    				Thread.sleep(millis);//多少毫秒尝试一次
    			} catch (InterruptedException ie) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			if(i == times - 1){//最后一次
    				throw new RuntimeException(e);
    			}
    			
    		}
    	}
		return sftpHelper;
    }
    
    public static void main(String[] args){
    	 //url
         String url ="120.26.111.153";
    	
         //用户名
    	 String user="bairong";
    	
    	 //密码
    	 String password ="Brong2017";
    	
    	 //保存文件的路径
    	 String ftpFilePat="/home/sftp/upload/";
    	
    	 //若连接不通重试的次数
    	 int times =2;
    	 
    		/**
    		 * 若连接不通，重试次数的间隔时间毫秒
    		 */
    	long millis=5000;
    	File file= new File("");
    	 
		if(LOGGER.isInfoEnabled())
			LOGGER.info("的SFTP方法，真正传数据");
		
		SftpHelper sftpHelper = SftpHelper.getSftpHelper(url, user, password, ftpFilePat,times,millis);
		if(LOGGER.isInfoEnabled()){
			LOGGER.info("sftpHelper.create()方法，成功...........................");
		}
//		sftpHelper.put(file.getParent() + "/", file.getName());
		sftpHelper.get("test.txt", "E:\\ftp\\");
		sftpHelper.close();
	
    }
}
