package com.examplesoft.ecommercemonolite.domain.campaign.service;

import com.examplesoft.ecommercemonolite.domain.campaign.dto.CampaignDto;
import com.examplesoft.ecommercemonolite.domain.campaign.dto.CampaignMapper;
import com.examplesoft.ecommercemonolite.domain.campaign.dto.CampaignSaveEvent;
import com.examplesoft.ecommercemonolite.domain.campaign.entity.Campaign;
import com.examplesoft.ecommercemonolite.domain.campaign.entity.CampaignScope;
import com.examplesoft.ecommercemonolite.domain.campaign.repo.CampaignRepository;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import com.examplesoft.ecommercemonolite.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository repository;
    private final ApplicationEventPublisher publisher;

    @Override
    public Page<CampaignDto> getAll(Pageable pageable) {
        return PageUtil.toPage(repository.findAll(pageable), CampaignMapper::toDto);
    }

    @Override
    public List<CampaignDto> getAllByTargets(Set<String> targets) {
        return repository.findAllByTargets(targets).stream()
                .map(CampaignMapper::toDto)
                .toList();
    }

    @Override
    public CampaignDto getById(String id) {
        return repository.findById(id).map(CampaignMapper::toDto).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Campaign.class.getSimpleName(),id));
    }

    @Override
    @Transactional
    public CampaignDto save(CampaignDto campaignDto) {
        CampaignDto campaign = CampaignMapper.toDto(repository.save(CampaignMapper.toEntity(new Campaign(),campaignDto)));

        if (campaignDto.getCampaignScope() == CampaignScope.PRODUCT) {
            publisher.publishEvent(new CampaignSaveEvent(campaign.getTargets()));
        }
        return campaign;
    }

    @Override
    @Transactional
    public CampaignDto update(String id, CampaignDto campaignDto) {
        Campaign campaign = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Campaign.class.getSimpleName(),id));
        return CampaignMapper.toDto(repository.save(CampaignMapper.toEntity(campaign,campaignDto)));
    }

    @Override
    @Transactional
    public void delete(String id) {
        Campaign campaign = repository.findById(id).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, Campaign.class.getSimpleName(),id));
        repository.delete(campaign);
    }
}
