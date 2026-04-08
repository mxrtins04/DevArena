package com.devarena.vote.service;

import java.util.UUID;

import com.devarena.vote.dto.response.VoteResponseDto;

public class VoteService {
    final UserRepository;
    final ProjectRepository;
    VoteService(VoterRepository voterRepo, ProjectRepository projectRepo)
    public VoteResponseDto castVote(UUID votersId, UUID projectId){
        User voter = 
    }
}
