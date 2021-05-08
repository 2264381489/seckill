package com.xiaoyan.eurekaconsumer.entity;

import org.springframework.stereotype.Component;

public class emailDTO {
  public String toUser;
  public String nickname;

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public emailDTO(String toUser, String nickname) {
        this.toUser = toUser;
        this.nickname = nickname;
    }
}
