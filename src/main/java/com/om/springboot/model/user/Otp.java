package com.om.springboot.model.user;

import com.om.springboot.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "otp", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"otp"
        })
})
public class Otp extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mobileNumber;
    private String otp;
    private Instant createdOn;
    private Instant resentOn;
    private Long resentCount;
}
