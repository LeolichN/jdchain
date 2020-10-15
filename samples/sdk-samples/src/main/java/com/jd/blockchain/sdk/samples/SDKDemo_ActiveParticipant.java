package com.jd.blockchain.sdk.samples;

import com.jd.blockchain.utils.http.converters.JsonResponseConverter;
import com.jd.blockchain.utils.web.model.WebResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangshuang
 * @Date: 2020/5/27 5:18 PM
 * Version 1.0
 */
public class SDKDemo_ActiveParticipant {

    // 接受激活参与方操作的共识节点Http服务地址， 根据具体环境配置进行修改
    private static String httpIp = "127.0.0.1";
    private static String httpPort = "7080";

    public static void main(String[] args) {

        String url = "http://" + httpIp + ":" + httpPort + "/management/delegate/activeparticipant";

        System.out.println("url = " + url);

        HttpPost httpPost = new HttpPost(url);

        List<BasicNameValuePair> para=new ArrayList<BasicNameValuePair>();

        // 账本值根据具体情况进行修改
        BasicNameValuePair base58LedgerHash = new BasicNameValuePair("ledgerHash",  "j5tuvAR3Q6ATsMNYTwt7SxVeCqd73itQbpmePxzSg6Zsxc");

        // 激活的新参与方的共识网络地址
        BasicNameValuePair host = new BasicNameValuePair("consensusHost",  "127.0.0.1");
        BasicNameValuePair port = new BasicNameValuePair("consensusPort", "16000");

        // 指定已经启动的其他共识节点的HTTP管理端口
        BasicNameValuePair manageHost = new BasicNameValuePair("remoteManageHost",  "127.0.0.1");
        BasicNameValuePair managePort = new BasicNameValuePair("remoteManagePort", "7083");

        para.add(base58LedgerHash);
        para.add(host);
        para.add(port);
        para.add(manageHost);
        para.add(managePort);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(para,"UTF-8"));
            HttpClient httpClient = HttpClients.createDefault();

            HttpResponse response = httpClient.execute(httpPost);
            JsonResponseConverter jsonConverter = new JsonResponseConverter(WebResponse.class);

            WebResponse webResponse = (WebResponse) jsonConverter.getResponse(null, response.getEntity().getContent(), null);
            System.out.println("response result = " + webResponse.isSuccess());
            if (!webResponse.isSuccess()) {
                System.out.println("error msg = " + webResponse.getError().getErrorMessage());
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Active participant post request error!");
        }
    }
}
