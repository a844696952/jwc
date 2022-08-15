package com.yice.edu.cn.ws.principal;

import javax.validation.constraints.NotNull;
import java.security.Principal;

public class UserPrincipal implements Principal  {
    @NotNull
    private String id; //用户id
    private String schoolId; //暂时不传
    private String groupName;
    private String clientName;//业务标识 eg:"cc"
    private String userName; //{teacherId}-{userType}用户标识

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String getName() {
        return this.id;
    }

    @Override
    public int hashCode() {
        if(schoolId!=null){
            return (id+schoolId).hashCode();
        }
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof UserPrincipal)){
            return false;
        }
        UserPrincipal userPrincipal=(UserPrincipal)obj;
        return this.id.equals(userPrincipal.getId());
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "id='" + id + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", name='" + this.getName() + '\'' +
                '}';
    }
}
