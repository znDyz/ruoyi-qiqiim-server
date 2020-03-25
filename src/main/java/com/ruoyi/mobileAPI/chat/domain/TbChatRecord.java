package com.ruoyi.mobileAPI.chat.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 聊天记录对象 tb_chat_record
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
public class TbChatRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 用户id */
    @Excel(name = "用户id")
    private String userid;

    /** 好友id */
    @Excel(name = "好友id")
    private String friendid;

    /** 是否已读 */
    @Excel(name = "是否已读")
    private Integer hasRead;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer hasDelete;

    /** 消息 */
    @Excel(name = "消息")
    private String message;

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

    public void setUserid(String userid) 
    {
        this.userid = userid;
    }
    public String getUserid() 
    {
        return userid;
    }

    public void setFriendid(String friendid) 
    {
        this.friendid = friendid;
    }
    public String getFriendid() 
    {
        return friendid;
    }

    public void setHasRead(Integer hasRead) 
    {
        this.hasRead = hasRead;
    }
    public Integer getHasRead() 
    {
        return hasRead;
    }

    public void setHasDelete(Integer hasDelete) 
    {
        this.hasDelete = hasDelete;
    }
    public Integer getHasDelete() 
    {
        return hasDelete;
    }

    public void setMessage(String message) 
    {
        this.message = message;
    }
    public String getMessage() 
    {
        return message;
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
            .append("userid", getUserid())
            .append("friendid", getFriendid())
            .append("hasRead", getHasRead())
            .append("createtime", getCreatetime())
            .append("hasDelete", getHasDelete())
            .append("message", getMessage())
            .toString();
    }
}
