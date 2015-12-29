package com.efeiyi.ec.yale.question.model;

import com.efeiyi.ec.organization.model.BigUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2015/12/29.
 */
@Entity
@Table(name = "yale_weixin_participator")
public class Participator extends BigUser {

    private String nickname;
    private String headimgurl;
    private String openid;

    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column(name = "headimg_url")
    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    @Column(name = "open_id")
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
