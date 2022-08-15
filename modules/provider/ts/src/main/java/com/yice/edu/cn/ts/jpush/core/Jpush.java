package com.yice.edu.cn.ts.jpush.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.CIDResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.pojo.ts.jpush.PushDetail;
import com.yice.edu.cn.ts.feignClient.PushDetailFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

@Component
public class Jpush {
    private static final Logger logger = LoggerFactory.getLogger(Jpush.class);
    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;
    @Autowired
    private PushDetailFeign pushDetailFeign;
    @Autowired
    private SequenceId sequenceId;
    private String getFiledName(String methodName){
        return StrUtil.lowerFirst(methodName.replaceFirst("^get|^is",""));
    }
    private PushPayload buildPayload(Push push) throws InvocationTargetException, IllegalAccessException {
        PushPayload.Builder builder = PushPayload.newBuilder().setPlatform(Platform.all());
        if(push.isAll()){
            builder.setAudience(Audience.all());
        }else{
            Audience.Builder audienceBuilder = Audience.newBuilder();
            if(push.getAlias()!=null){
                audienceBuilder.addAudienceTarget(AudienceTarget.alias(push.getAlias()));
            }
            if(push.getTag()!=null){
                audienceBuilder.addAudienceTarget(AudienceTarget.tag(push.getTag()));
            }
            builder.setAudience(audienceBuilder.build());
        }
        Push.Notification notify = push.getNotification();
        if(notify!=null){
            AndroidNotification.Builder androidBuilder = AndroidNotification.newBuilder().setAlert(notify.getAlert()).setTitle(notify.getTitle());
            IosNotification.Builder iosBuilder = IosNotification.newBuilder().setContentAvailable(true).setAlert(notify.getAlert()).setSound(notify.getSound());
            Extras extras = notify.getExtras();
            if(extras!=null){
                Class<? extends Extras> clazz = extras.getClass();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    String name = method.getName();
                    if((name.startsWith("get")||name.startsWith("is"))&&!name.equals("getClass")){
                        Class<?> returnType = method.getReturnType();
                        String returnTypeName = returnType.getSimpleName();
                        String filedName = getFiledName(name);
                        final Object value = method.invoke(extras);
                        if(value!=null){
                            if(returnTypeName.equals("String")){
                                androidBuilder.addExtra(filedName,(String) value);
                                iosBuilder.addExtra(filedName,(String) value);
                            }else if(returnTypeName.equals("Integer")){
                                androidBuilder.addExtra(filedName,(Integer) value);
                                iosBuilder.addExtra(filedName,(Integer) value);
                            }else if(returnTypeName.equals("Double")){
                                androidBuilder.addExtra(filedName,(Double) value);
                                iosBuilder.addExtra(filedName,(Double) value);
                            }else if(returnTypeName.equals("Boolean")){
                                androidBuilder.addExtra(filedName,(Boolean) value);
                                iosBuilder.addExtra(filedName,(Boolean) value);
                            }
                        }


                    }
                }

            }
            builder.setNotification(Notification.newBuilder()
                    .addPlatformNotification(androidBuilder.build())
                    .addPlatformNotification(iosBuilder.build())
                    .build());
        }
        Push.Message message = push.getMessage();
        if(message!=null){
            Message.Builder messageBuilder = Message.newBuilder().setContentType(message.getContentType()).setMsgContent(message.getMsgContent())
                    .setTitle(message.getTitle());
            Extras extras = message.getExtras();
            if(extras !=null){
                Class<? extends Extras> clazz = extras.getClass();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    String name = method.getName();
                    if((name.startsWith("get")||name.startsWith("is"))&&!name.equals("getClass")){
                        Class<?> returnType = method.getReturnType();
                        String returnTypeName = returnType.getSimpleName();
                        String filedName = getFiledName(name);
                        final Object value = method.invoke(extras);
                        if(value!=null){
                            if(returnTypeName.equals("String")){
                                messageBuilder.addExtra(filedName,(String)value);
                            }else if(returnTypeName.equals("Integer")){
                                messageBuilder.addExtra(filedName,(Integer)value);
                            }else if(returnTypeName.equals("Double")){
                                messageBuilder.addExtra(filedName,(Double)value);
                            }else if(returnTypeName.equals("Boolean")){
                                messageBuilder.addExtra(filedName,(Boolean)value);
                            }
                        }


                    }
                }
            }
            builder.setMessage(messageBuilder.build());
        }
        Push.Options options = push.getOptions();
        Options.Builder optionsBuilder = Options.newBuilder().setApnsProduction(isProd);
        if(options!=null){
            optionsBuilder.setSendno(options.getSendno()).setTimeToLive(options.getTimeToLive()).setBigPushDuration(options.getBigPushDuration());
        }
        builder.setOptions(optionsBuilder.build());
        return builder.build();
    }


    public void sendPush(Push push) throws InvocationTargetException, IllegalAccessException {
        //新增把推送数据保存到数据库中
        if(Optional.ofNullable(push).map(p->p.getPushDetail()).isPresent()){
            PushDetail pushDetail = push.getPushDetail();
            pushDetail.setId(sequenceId.nextId());
            //设置发送拓展字段中添加 推送详情的记录id 方便标记已阅
            Extras extras = new Extras();
            extras.setId(pushDetail.getId());
            Optional.ofNullable(push.getNotification()).map(n->{n.setExtras(Optional.ofNullable(n.getExtras()).map(e->{e.setId(pushDetail.getId());return e;}).orElse(extras));return n;});
            Optional.ofNullable(push.getMessage()).map(m->{m.setExtras(Optional.ofNullable(m.getExtras()).map(e->{e.setId(pushDetail.getId());return e;}).orElse(extras));return m;});
            pushDetail.setSendDate(DateUtil.now());
            pushDetailFeign.savePushDetail4Push(pushDetail);
        }
        PushPayload payload =this.buildPayload(push);
        for (int i = 0; i < push.getKeys().length; i++) {
            JPushClient jpushClient = new JPushClient(push.getSecrets()[i], push.getKeys()[i], null, ClientConfig.getInstance());
            try {
                PushResult result = jpushClient.sendPush(payload);

            } catch (APIConnectionException e) {
                // Connection error, should retry later
                logger.error("Connection error, should retry later", e);

            } catch (APIRequestException e) {
                // Should review the error, and fix the request
                logger.error("Should review the error, and fix the request", e);
                logger.info("HTTP Status: " + e.getStatus());
                logger.info("Error Code: " + e.getErrorCode());
                logger.info("Error Message: " + e.getErrorMessage());
            }
        }

    }
    public static void main(String[] args) {

       /* Extras extras = new Extras(1,"yes",true);
        Class<? extends Extras> clazz = extras.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if((name.startsWith("get")||name.startsWith("is"))&&!name.equals("getClass")){
                Class<?> returnType = method.getReturnType();
                String simpleName = returnType.getSimpleName();
                if(simpleName.equals("String")){

                }
            }
        }*/
        System.out.println(UUID.randomUUID().toString());
        System.out.println("9b64594a-e8a8-415a-ae6e-ce72f52ed39a");
    }
}
