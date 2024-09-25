package com.examplesoft.ecommercemonolite.domain.campaign.repo;

import com.examplesoft.ecommercemonolite.domain.campaign.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CampaignRepository extends JpaRepository<Campaign, String> {
    @Query("SELECT c FROM Campaign c JOIN c.targets t WHERE t IN :targets AND c.isActive=TRUE")
    List<Campaign> findAllByTargetsAndIsActiveTrue(@Param("targets") Set<String> targets);
}
