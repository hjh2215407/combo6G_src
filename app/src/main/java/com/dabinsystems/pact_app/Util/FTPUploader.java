package com.dabinsystems.pact_app.Util;

import com.dabinsystems.pact_app.Handler.FunctionHandler;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class FTPUploader {

    FTPClient ftp = null;

    //param( host server ip, username, password )
    public FTPUploader(String host, String user, String pwd) throws Exception{
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }

    public void uploadFile(InputStream is, String fileName, String hostDir) {

        try {
//            ftp.makeDirectory(hostDir);

            FunctionHandler.getInstance().getDataConnector().removeCallback();
            this.ftp.storeFile(hostDir + fileName, is);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){

        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }

    }

    public FTPClient getFtp() {
        return ftp;
    }

}

/*
	File put_file = new File(s_srcfilename);

	    FileInputStream inputStream = new FileInputStream(put_file);

		final long Lfilesize =  put_file.length();

	     //inputstream을 countinginputstream으로 넘김

	    CountingInputStream cos = new CountingInputStream(inputStream){

	    	  protected void afterRead(int n){

		            super.afterRead(n);
//전송현황을 로그로 표시함.

		            InitActivity.logMsg("FTP", "Uploaded "+getCount() + "/" + Lfilesize);

		        }

	    };

	 //sotrefile 메서드에    countinginputstream을 넘겨줌

	    boolean result = m_ftpClient.storeFile(s_destfilename, cos);

	    inputStream.close();


출처: https://darkcher.tistory.com/entry/orgapachecommonsnetftpFTPClient-업로드-진행-현황-보기-ftpclient-progress-bar [우랄랄]
 */