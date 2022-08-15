package com.yice.edu.cn.common.util.jmessage.resource;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yice.edu.cn.common.util.jmessage.common.BaseClient;
import com.yice.edu.cn.common.util.jmessage.common.JMessageConfig;

import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jiguang.common.utils.Preconditions;

public class ResourceClient extends BaseClient {

    private static Logger LOG = LoggerFactory.getLogger(ResourceClient.class);

    private String resourcePath;
    private String authCode;


    public ResourceClient(String appkey, String masterSecret) {
        this(appkey, masterSecret, null, JMessageConfig.getInstance());
    }

    public ResourceClient(String appkey, String masterSecret, HttpProxy proxy) {
        this(appkey, masterSecret, proxy, JMessageConfig.getInstance());
    }

    /**
     * Create a JMessage Base Client
     *
     * @param appkey       The KEY of one application on JPush.
     * @param masterSecret API access secret of the appKey.
     * @param proxy        The proxy, if there is no proxy, should be null.
     * @param config       The client configuration. Can use JMessageConfig.getInstance() as default.
     */
    public ResourceClient(String appkey, String masterSecret, HttpProxy proxy, JMessageConfig config) {
        super(appkey, masterSecret, proxy, config);
        this.resourcePath = (String) config.get(JMessageConfig.RESOURCE_PATH);
        this.authCode = ServiceHelper.getBasicAuthorization(appkey, masterSecret);
    }

    /**
     * Download file with mediaId, will return DownloadResult which include url.
     * @param mediaId Necessary
     * @return DownloadResult
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public DownloadResult downloadFile(String mediaId)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != mediaId, "mediaId is necessary");

        ResponseWrapper response = _httpClient.sendGet(_baseUrl + resourcePath + "?mediaId=" + mediaId);
        return DownloadResult.fromResponse(response, DownloadResult.class);
    }

    /**
     * Upload file, only support image file(jpg, bmp, gif, png) currently,
     * file size should not larger than 8M.
     * @param path Necessary, the native path of the file you want to upload
     * @param fileType should be "image" or "file" or "voice"
     * @return UploadResult
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public UploadResult uploadFile(String path, String fileType)
            throws APIConnectionException, APIRequestException {
        Preconditions.checkArgument(null != path, "filename is necessary");
        Preconditions.checkArgument(fileType.equals("image") || fileType.equals("file") || fileType.equals("voice"), "Illegal file type!");
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            long fileSize = file.length();
            if (fileSize > 8 * 1024 * 1024) {
                throw new IllegalArgumentException("File size should not larger than 8M");
            }
            try {
                // 换行符
                final String newLine = "\r\n";
                final String boundaryPrefix = "--";
                // 定义数据分隔线
                String BOUNDARY = "========7d4a6d158c9";
                URL url = new URL(_baseUrl + resourcePath + "?type=" + fileType);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("Authorization", this.authCode);
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
                OutputStream out = new DataOutputStream(conn.getOutputStream());
                StringBuilder sb = new StringBuilder();
                sb.append(boundaryPrefix);
                sb.append(BOUNDARY);
                sb.append(newLine);
                sb.append("Content-Disposition: form-data;name=\"" + fileType + "\";filename=\"" + path + "\"" + newLine);
                sb.append("Content-Type:application/octet-stream");
                // 参数头设置完以后需要两个换行，然后才是参数内容
                sb.append(newLine);
                sb.append(newLine);
                out.write(sb.toString().getBytes());
                DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                byte[] bufferOut = new byte[1024];
                int bytes = 0;
                while ((bytes = dataInputStream.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                out.write(newLine.getBytes());
                dataInputStream.close();
                // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
                byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                        .getBytes();
                // 写上结尾标识
                out.write(end_data);
                out.flush();
                out.close();
                LOG.debug("Send request - POST"  + " " + url);

                int status1 = conn.getResponseCode();
                StringBuffer stringBuffer = new StringBuffer();
                InputStream in = null;
                if(status1 / 100 == 2) {
                    in = conn.getInputStream();
                } else {
                    in = conn.getErrorStream();
                }
                if(null != in) {
                    InputStreamReader responseContent = new InputStreamReader(in, "UTF-8");
                    char[] quota = new char[1024];

                    int remaining;
                    while((remaining = responseContent.read(quota)) > 0) {
                        stringBuffer.append(quota, 0, remaining);
                    }
                }
                ResponseWrapper wrapper = new ResponseWrapper();
                String responseContent1 = stringBuffer.toString();
                wrapper.responseCode = status1;
                wrapper.responseContent = responseContent1;
                String quota1 = conn.getHeaderField("X-Rate-Limit-Limit");
                String remaining1 = conn.getHeaderField("X-Rate-Limit-Remaining");
                String reset = conn.getHeaderField("X-Rate-Limit-Reset");
                wrapper.setRateLimit(quota1, remaining1, reset);
                if(status1 >= 200 && status1 < 300) {
                    LOG.debug("Succeed to get response OK - responseCode:" + status1);
                    LOG.debug("Response Content - " + responseContent1);
                } else {
                    if(status1 < 300 || status1 >= 400) {
                        LOG.warn("Got error response - responseCode:" + status1 + ", responseContent:" + responseContent1);
                        wrapper.setErrorObject();
                        throw new APIRequestException(wrapper);
                    }

                    LOG.warn("Normal response but unexpected - responseCode:" + status1 + ", responseContent:" + responseContent1);
                }
                return UploadResult.fromResponse(wrapper, UploadResult.class);
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("File name is invalid, please check again");
        }
        return null;
    }
    
    /**
     * 
     * 上传远程的图片
     * 
     * Upload file, only support image file(jpg, bmp, gif, png) currently,
     * file size should not larger than 8M.
     * @param httpPath Necessary, the remote path of the file you want to upload
     * @param fileType should be "image"
     * @return UploadResult
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public UploadResult uploadFileByRemoteImage(String httpPath, String fileType)
            throws APIConnectionException, APIRequestException {
    	   DataInputStream imageIn = null;
    	   OutputStream out = null;
    	   
    	   InputStream resultIn = null;
    	   InputStreamReader responseContent = null;
            try {
            	URL imageUrl = new URL(httpPath);
            	URLConnection con = imageUrl.openConnection();
            	con.setConnectTimeout(5*1000);
            	imageIn = new DataInputStream(con.getInputStream());
                // 换行符
                final String newLine = "\r\n";
                final String boundaryPrefix = "--";
                // 定义数据分隔线
                String BOUNDARY = "========7d4a6d158c9";
                URL url = new URL(_baseUrl + resourcePath + "?type=" + fileType);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("Authorization", this.authCode);
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
                out = new DataOutputStream(conn.getOutputStream());
                StringBuilder sb = new StringBuilder();
                sb.append(boundaryPrefix); 
                sb.append(BOUNDARY);
                sb.append(newLine);
                sb.append("Content-Disposition: form-data;name=\"" + fileType + "\";filename=\"" + httpPath.replace(".jpeg", ".jpg").replace(".JPEG", ".JPG") + "\"" + newLine);
                sb.append("Content-Type:application/octet-stream");
                // 参数头设置完以后需要两个换行，然后才是参数内容
                sb.append(newLine);
                sb.append(newLine);
                out.write(sb.toString().getBytes());
                byte[] bufferOut = new byte[1024];
                int bytes = 0;
                while ((bytes = imageIn.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                out.write(newLine.getBytes());
                imageIn.close();
                // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
                byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                        .getBytes();
                // 写上结尾标识
                out.write(end_data);
                out.flush();
                out.close();
                LOG.debug("Send request - POST"  + " " + url);

                int status1 = conn.getResponseCode();
                StringBuffer stringBuffer = new StringBuffer();
                if(status1 / 100 == 2) {
                	resultIn = conn.getInputStream();
                } else {
                	resultIn = conn.getErrorStream();
                }
                if(null != resultIn) {
                    responseContent = new InputStreamReader(resultIn, "UTF-8");
                    char[] quota = new char[1024];

                    int remaining;
                    while((remaining = responseContent.read(quota)) > 0) {
                        stringBuffer.append(quota, 0, remaining);
                    }
                }
                ResponseWrapper wrapper = new ResponseWrapper();
                String responseContent1 = stringBuffer.toString();
                wrapper.responseCode = status1;
                wrapper.responseContent = responseContent1;
                String quota1 = conn.getHeaderField("X-Rate-Limit-Limit");
                String remaining1 = conn.getHeaderField("X-Rate-Limit-Remaining");
                String reset = conn.getHeaderField("X-Rate-Limit-Reset");
                wrapper.setRateLimit(quota1, remaining1, reset);
                if(status1 >= 200 && status1 < 300) {
                    LOG.debug("Succeed to get response OK - responseCode:" + status1);
                    LOG.debug("Response Content - " + responseContent1);
                } else {
                    if(status1 < 300 || status1 >= 400) {
                        LOG.warn("Got error response - responseCode:" + status1 + ", responseContent:" + responseContent1);
                        wrapper.setErrorObject();
                        throw new APIRequestException(wrapper);
                    }

                    LOG.warn("Normal response but unexpected - responseCode:" + status1 + ", responseContent:" + responseContent1);
                }
                return UploadResult.fromResponse(wrapper, UploadResult.class);
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error(e.getMessage());
            }finally {
              try {
            	if(responseContent!=null) {
            		responseContent.close();
            	}
            	if(resultIn!=null) {
            	   resultIn.close();
            	}
            	if(out!=null) {
				   out.close();
            	 }
            	if(imageIn!=null) {
            		imageIn.close();
            	 }
				} catch (IOException e) {
					LOG.error(e.getMessage());
            	}
            	
            }
        return null;
    }


}
