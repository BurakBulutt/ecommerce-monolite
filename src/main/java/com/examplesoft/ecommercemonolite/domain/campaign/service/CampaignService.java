package com.examplesoft.ecommercemonolite.domain.campaign.service;

import com.examplesoft.ecommercemonolite.domain.campaign.dto.CampaignDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface CampaignService {
    Page<CampaignDto> getAll(Pageable pageable);
    List<CampaignDto> getAllByTargets(Set<String> targets);
    CampaignDto getById(String id);
    CampaignDto save(CampaignDto campaignDto);
    CampaignDto update(String id,CampaignDto campaignDto);
    void delete(String id);

    void campaignNotify(String id);
}
