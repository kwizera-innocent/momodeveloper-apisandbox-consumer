package rw.momo.api.momoapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rw.momo.api.momoapi.model.EStatus;
import rw.momo.api.momoapi.model.PayResponse;

@Repository
public interface ResponseRepository extends JpaRepository<PayResponse, UUID> {
    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("update PayResponse p set p.status = :status where p.referenceId = :referenceId")
	void updateStatus(@Param(value = "referenceId") String referenceId, @Param(value = "status") EStatus eStatus);
    
}
