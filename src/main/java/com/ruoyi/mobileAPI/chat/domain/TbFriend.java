package com.ruoyi.mobileAPI.chat.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 聊天功能对象 tb_friend
 * 
 * @author ruoyi
 * @date 2020-03-22
 */
public class TbFriend extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 用户id */
    @Excel(name = "用户id")
    private String userid;

    /** 好友id */
    @Excel(name = "好友id")
    private String friendsId;

    /** 备注 */
    @Excel(name = "备注")
    private String comments;

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
    public void setFriendsId(String friendsId) 
    {
        this.friendsId = friendsId;
    }

    public String getFriendsId() 
    {
        return friendsId;
    }
    public void setComments(String comments) 
    {
        this.comments = comments;
    }

    public String getComments() 
    {
        return comments;
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
            .append("friendsId", getFriendsId())
            .append("comments", getComments())
            .append("createtime", getCreatetime())
            .toString();
    }
}
