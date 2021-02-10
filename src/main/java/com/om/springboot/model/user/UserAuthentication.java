package com.om.springboot.model.user;

import com.om.springboot.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "user_authentication")
public class UserAuthentication extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long adminId;
    String mobile;
    int isLoggedIn;
    String otp;
    Instant loginTime;
    Instant logoutTime;
}
