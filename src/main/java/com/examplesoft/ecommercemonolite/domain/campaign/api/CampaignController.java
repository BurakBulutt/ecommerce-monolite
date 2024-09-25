package com.examplesoft.ecommercemonolite.domain.campaign.api;

import com.examplesoft.ecommercemonolite.domain.campaign.service.CampaignService;
import com.examplesoft.ecommercemonolite.util.BaseController;
import com.examplesoft.ecommercemonolite.util.PageResponse;
import com.examplesoft.ecommercemonolite.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("campaigns")
@RequiredArgsConstructor
public class CampaignController extends BaseController {
    private final CampaignService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<PageResponse<CampaignResponse>> getAll(Pageable pageable) {
        return response(CampaignMapper.toPageResponse(service.getAll(pageable)));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<CampaignResponse> getById(@PathVariable String id) {
        return response(CampaignMapper.toResponse(service.getById(id)));
    }

    @GetMapping("/campaign-notify/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<Void> campaignNotify(@PathVariable String id) {
        service.campaignNotify(id);
        return success();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<CampaignResponse> save(@RequestBody CampaignRequest request) {
        return response(CampaignMapper.toResponse(service.save(CampaignMapper.toDto(request))));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<CampaignResponse> update(@PathVariable String id, @RequestBody CampaignRequest request) {
        return response(CampaignMapper.toResponse(service.update(id, CampaignMapper.toDto(request))));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Response<Void> delete(@PathVariable String id) {
        service.delete(id);
        return success();
    }
}
