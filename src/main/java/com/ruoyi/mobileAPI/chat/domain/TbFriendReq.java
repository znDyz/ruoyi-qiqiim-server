package com.ruoyi.mobileAPI.chat.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 好友请求对象 tb_friend_req
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
public class TbFriendReq extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 请求好友用户id */
    @Excel(name = "请求好友用户id")
    private String fromUserid;

    /** 被请求好友用户id */
    @Excel(name = "被请求好友用户id")
    private String toUserid;

    /** 发送的消息 */
    @Excel(name = "发送的消息")
    private String message;

    /** 是否已处理 */
    @Excel(name = "是否已处理")
    private Integer status;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Date createtime;

    public void setId(String id)
    {
        this.id = id;
    }
    public String getId() 
    {
        return id;
    }

    public void setFromUserid(String fromUserid) 
    {
        this.fromUserid = fromUserid;
    }
    public String getFromUserid() 
    {
        return fromUserid;
    }

    public void setToUserid(String toUserid) 
    {
        this.toUserid = toUserid;
    }
    public String getToUserid() 
    {
        return toUserid;
    }

    public void setMessage(String message) 
    {
        this.message = message;
    }
    public String getMessage() 
    {
        return message;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }
    public Integer getStatus() 
    {
        return status;
    }

    public void setCreatetime(Date createtime)
    {
        this.createtime = createtime;
    }
    public Date getCreatetime()
    {
        return createtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fromUserid", getFromUserid())
            .append("toUserid", getToUserid())
            .append("createtime", getCreatetime())
            .append("message", getMessage())
            .append("status", getStatus())
            .toString();
    }
}
