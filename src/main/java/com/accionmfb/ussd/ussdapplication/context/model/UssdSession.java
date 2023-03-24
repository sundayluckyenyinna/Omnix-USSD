package com.accionmfb.ussd.ussdapplication.context.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ussd_session")
@Getter
@Setter
public class UssdSession
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "context_data")
    private String contextData;

    @Column(name = "mobileNumber")
    private String mobileNumber;

    @Column(name = "telco")
    private String telco;

    @Column(name = "session_start_at")
    private Date sessionStartDate;

    @Column(name = "session_end_at")
    private Date sessionEndDate;
}
