package com.accionmfb.ussd.ussdapplication.context.repo;

import com.accionmfb.ussd.ussdapplication.context.model.UssdSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UssdSessionRepository extends JpaRepository<UssdSession, Long>
{
    UssdSession findBySessionId(String sessionId);
}
