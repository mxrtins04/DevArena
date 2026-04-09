package com.devarena.vote.service.impl;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.devarena.vote.dto.response.VoteResponseDto;
import com.devarena.vote.entity.Vote;
import com.devarena.vote.service.VoteService;
import com.devarena.exception.ResourceNotFoundException;
import com.devarena.project.entity.Project;
import com.devarena.project.repository.ProjectRepository;
import com.devarena.user.entity.User;
import com.devarena.user.repository.UserRepository;
import com.devarena.vote.repository.VoteRepository;
import org.springframework.transaction.annotation.Isolation;

public class VoteServiceImpl implements VoteService{
    private final UserRepository userRepo;
    private final ProjectRepository projectRepo;
    private final VoteRepository voteRepo;

    public VoteServiceImpl(UserRepository userRepository, ProjectRepository projectRepository, VoteRepository voteRepository){
        this.userRepo = userRepository;
        this.projectRepo = projectRepository;
        this.voteRepo = voteRepository;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public VoteResponseDto castVote(UUID votersId, UUID projectId){
        User voter = userRepo.findById(votersId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        
        if (voteRepo.existsByVoterIdAndProjectId(votersId, projectId)) {
            throw new IllegalStateException("User has already voted for this project");
        }

        Vote vote = Vote.builder()
                .voter(voter)
                .project(project)
                .build();

        voteRepo.save(vote);
        projectRepo.incrementVoteCount(projectId);
        return toResponseDto(vote);

    }

    @Transactional(readOnly = true)
    public List<VoteResponseDto> getVotesForProject(UUID projectId, UUID voterId) {
        return voteRepo.findByVoterIdAndProjectId(voterId, projectId).stream()
            .map(this::toResponseDto)
            .collect(Collectors.toList());
    }

    private VoteResponseDto toResponseDto(Vote vote) {
        return new VoteResponseDto(
            vote.getId(),
            vote.getVoter().getId(),
            vote.getProject().getId(),
            vote.getCreatedAt()
        );
    }
}

