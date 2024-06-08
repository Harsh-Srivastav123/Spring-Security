package com.sprinsecurity.security.events;

import com.sprinsecurity.security.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class VerifyTokenEvent extends ApplicationEvent {

    User user;
    String  url;
    public VerifyTokenEvent(User user, String url) {
        super(user);
        this.user=user;
        this.url=url;
    }
}
