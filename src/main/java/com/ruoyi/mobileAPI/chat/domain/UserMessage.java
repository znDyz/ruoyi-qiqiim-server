package com.ruoyi.mobileAPI.chat.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 聊天功能对象 user_message
 * 
 * @author ruoyi
 * @date 2020-03-29
 */
public class UserMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 发送人 */
    @Excel(name = "发送人")
    private String senduser;

    /** 接收人 */
    @Excel(name = "接收人")
    private String receiveuser;

    /** 群ID */
    @Excel(name = "群ID")
    private String groupid;

    /** 是否已读 0 未读  1 已读 */
    @Excel(name = "是否已读 0 未读  1 已读")
    private Long isread;

    /** 类型 0 单聊消息  1 群消息 */
    @Excel(name = "类型 0 单聊消息  1 群消息")
    private Long type;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String content;

    /** $column.columnComment */
    @Excel(name = "消息内容")
    private Long createuser;

    /** $column.columnComment */
    @Excel(name = "消息内容", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** $column.columnComment */
    @Excel(name = "消息内容", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSenduser(String senduser) 
    {
        this.senduser = senduser;
    }

    public String getSenduser() 
    {
        return senduser;
    }
    public void setReceiveuser(String receiveuser) 
    {
        this.receiveuser = receiveuser;
    }

    public String getReceiveuser() 
    {
        return receiveuser;
    }
    public void setGroupid(String groupid) 
    {
        this.groupid = groupid;
    }

    public String getGroupid() 
    {
        return groupid;
    }
    public void setIsread(Long isread) 
    {
        this.isread = isread;
    }

    public Long getIsread() 
    {
        return isread;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setCreateuser(Long createuser) 
    {
        this.createuser = createuser;
    }

    public Long getCreateuser() 
    {
        return createuser;
    }
    public void setCreatedate(Date createdate) 
    {
        this.createdate = createdate;
    }

    public Date getCreatedate() 
    {
        return createdate;
    }
    public void setUpdatedate(Date updatedate) 
    {
        this.updatedate = updatedate;
    }

    public Date getUpdatedate() 
    {
        return updatedate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("senduser", getSenduser())
            .append("receiveuser", getReceiveuser())
            .append("groupid", getGroupid())
            .append("isread", getIsread())
            .append("type", getType())
            .append("content", getContent())
            .append("createuser", getCreateuser())
            .append("createdate", getCreatedate())
            .append("updatedate", getUpdatedate())
            .toString();
    }
}
