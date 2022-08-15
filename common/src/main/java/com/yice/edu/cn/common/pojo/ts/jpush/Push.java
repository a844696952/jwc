package com.yice.edu.cn.common.pojo.ts.jpush;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.Constant;
import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/#options
 * 推送实体类，构建好后发送给redis通道，mcs接受处理发送
 */
@Data
public class Push {
    private String[] keys;//极光推送的key
    private String[] secrets;//极光推送的secret
    private boolean all;//是否推送所有人,如果为true则tag和alias失效
    private String[] tag;//目前app端存放了schoolId
    private String[] alias;//app端存放了教师id
    private Notification notification;//通知
    private Message message;//普通消息
    private Options options;//设置推送存活时间等
    private PushDetail pushDetail;//设置推送内容 保存到数据库中 PS:不用保存数据库中可以不设则改值
    @Data
    public static class Notification{
        private String alert;
        private String title;
        private String referenceId;
        private String sendDate;
        private String sound= Constant.JPUSH.SOUND_1;
        private Extras extras;
        public static Builder newBuilder(){
            return new Builder();
        }
        public static class Builder{
            private Notification notification;
            Builder() {
                this.notification = new Notification();
            }
            public Builder setAlert(String alert){
                this.notification.alert=alert;
                return this;
            }
            public Builder setTitle(String title){
                this.notification.title=title;
                return this;
            }
            public Builder setSound(String sound){
                this.notification.sound=sound;
                return this;
            }
            public Builder setExtras(Extras extras){
                this.notification.extras=extras;
                return this;

            }
            public Builder setReferenceId(String referenceId){
                this.notification.referenceId=referenceId;
                return this;
            }

            public Builder setSendDate(String sendDate){
                this.notification.sendDate=sendDate;
                return this;
            }


            public Notification build(){
                return this.notification;
            }
        }
    }
    @Data
    public static class Message{
        private String msgContent;
        private String title;
        private String contentType;
        private String referenceId;
        private Extras extras;
        public static Builder newBuilder(){
            return new Builder();
        }
        public static class Builder{
            private Message message;
            Builder() {
                this.message = new Message();
            }
            public Builder setAlert(String msgContent){
                this.message.msgContent =msgContent;
                return this;
            }
            public Builder setTitle(String title){
                this.message.title=title;
                return this;
            }
            public Builder setContentType(String contentType){
                this.message.contentType =contentType;
                return this;
            }
            public Builder setMsgContent(String msgContent){
                this.message.msgContent =msgContent;
                return this;
            }
            public Builder setReferenceId(String referenceId){
                 this.message.referenceId=referenceId;
                 return this;
            }



            public Builder setExtras(Extras extras){
                this.message.extras=extras;
                return this;
            }
            public Message build(){
                if(this.message.msgContent==null){
                    throw new RuntimeException("message中msgContent必传");
                }
                return this.message;
            }
        }
    }
    @Data
    public static class Options{
        private int sendno;
        private int timeToLive =86400;//最多到864000,10天
        private int bigPushDuration;
        public static Builder newBuilder(){
            return new Builder();
        }
        public static class Builder{
            private Options options;
            Builder() {
                this.options = new Options();
            }
            public Builder setSendno(int sendno){
                this.options.sendno=sendno;
                return this;
            }
            public Builder setTimeToLive(int timeToLive){
                this.options.timeToLive =timeToLive;
                return this;
            }
            public Builder setBigPushDuration(int bigPushDuration){
                this.options.bigPushDuration =bigPushDuration;
                return this;
            }
            public Options build(){
                return this.options;
            }
        }
    }
    public static Builder newBuilder(JpushApp jpushApp){
        return new Builder(jpushApp);
    }
    public static class Builder{
        private Push push;
        private Builder(){

        }
        Builder(JpushApp... jpushApps){
            this.push = new Push();
            push.keys=new String[jpushApps.length];
            push.secrets=new String[jpushApps.length];
            for (int i = 0; i < jpushApps.length; i++) {
                push.keys[i]=jpushApps[i].getKey();
                push.secrets[i]=jpushApps[i].getSecret();
            }
        }
        public Builder setAll(boolean all){
            this.push.all=all;
            return this;
        }
        public Builder setTag(String... tag){
            this.push.tag=tag;
            return this;
        }
        public Builder setAlias(String... alias){
            this.push.alias=alias;
            return this;
        }
        public Builder setNotification(Notification notification){
            this.push.notification=notification;
            return this;
        }
        public Builder setMessage(Message message){
            this.push.message=message;
            return this;
        }
        public Builder setOptions(Options options){
            this.push.options=options;
            return this;
        }
        public Builder setPushDetail(PushDetail pushDetail){
            this.push.pushDetail=pushDetail;
            return this;
        }
        public Push build(){
            if(push.all){
                push.tag=null;
                push.alias=null;
            }else if(push.alias==null&&push.tag==null){
                throw new IllegalArgumentException("alias或者tag至少设置一个");
            }
            if(push.notification==null&&push.message==null){
                throw new IllegalArgumentException("notification或者message至少设置一个");
            }
            if(push.getNotification()!=null&&push.getNotification().getExtras()!=null){
                if(push.getNotification().getExtras().getType()==null){
                    throw new IllegalArgumentException("含有extras扩展字段时,type字段必填");
                }
            }
            if(push.getMessage()!=null&&push.getMessage().getExtras()!=null){
                if(push.getMessage().getExtras().getType()==null){
                    throw new IllegalArgumentException("含有extras扩展字段时,type字段必填");
                }
            }

            return this.push;
        }

        /**
         * 简单的推送消息 就 标题和简单内容 不携带详情id
         * 不包含透传
         * @param alias
         * @param title
         * @param alert
         * @param extrasType
         * @return
         */
        public Push getSimplePush(String[] alias,String title,String alert,Integer extrasType){
            Map<String,Object> content = new HashMap<>();
            content.put("title",title);
            content.put("content",alert);
            return this.setAlias(alias)
                    .setNotification(Push.Notification.newBuilder().setTitle(title).setAlert(alert).setExtras(Extras.newBuilder().setType(extrasType).build()).setSound(Constant.JPUSH.SOUND_1).build())
                    .setPushDetail(PushDetail.newBuilder().setReceiverIds(Arrays.asList(alias)).setType(extrasType).setContent(content).build())
                    .build();
        }
        /**
         * 简单的推送消息 就 标题和简单内容 携带详情id
         * 不包含透传
         * @param alias
         * @param title
         * @param alert
         * @param extrasType
         * @param referenceId 详情id
         * @return
         */
        public Push getSimplePusByRefrenceId(String[] alias,String title,String alert,Integer extrasType,String referenceId){
            Map<String,Object> content = new HashMap<>();
            content.put("title",title);
            content.put("content",alert);
            content.put("referenceId",referenceId);
            content.put("sendDate",DateUtil.now());
            return this.setAlias(alias)
                    .setNotification(Push.Notification.newBuilder().setTitle(title).setAlert(alert).setReferenceId(referenceId).
                            setSendDate(DateUtil.now()).setExtras(Extras.newBuilder().setType(extrasType).setReferenceId(referenceId).setSendDate(DateUtil.now()).build()).
                            setSound(Constant.JPUSH.SOUND_1).build())
                    .setPushDetail(PushDetail.newBuilder().setReceiverIds(Arrays.asList(alias)).setType(extrasType).setReferenceId(referenceId).setContent(content).setSendDate(DateUtil.now()).build())
                    .build();
        }


        /**
         * 推送复杂的内容体 内容自定义 放在 map中 自己约束
         * 不包含透传
         * @param alias
         * @param title
         * @param alert
         * @param extrasType
         * @param expand 自定义拓展的其他字段
         * @return
         */
        public Push getComplexPush(String[] alias,String title,String alert,Integer extrasType,Map<String,Object> expand){
            Map<String,Object> content = new HashMap<>();
            content.put("title",title);
            content.put("content",alert);
            content.putAll(expand);
            return this.setAlias(alias)
                    .setPushDetail(PushDetail.newBuilder().setReceiverIds(Arrays.asList(alias)).setType(extrasType).setContent(content).build())
                    .setNotification(Push.Notification.newBuilder().setTitle(title).setAlert(alert).setExtras(Extras.newBuilder().setType(extrasType).setJSON(JSON.toJSONString(expand)).build()).setSound(Constant.JPUSH.SOUND_1).build())
                    .build();
        }

    }

}
