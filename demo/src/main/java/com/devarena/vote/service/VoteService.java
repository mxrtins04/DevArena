package com.devarena.vote.service;

import java.util.List;
import java.util.UUID;

import com.devarena.vote.dto.response.VoteResponseDto;

public interface VoteService{
    public VoteResponseDto castVote(UUID votersId, UUID projectId);

    public List<VoteResponseDto> getVotesForProject(UUID projectId, UUID voterId);

}