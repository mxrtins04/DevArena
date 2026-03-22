package com.devarena.project.service;

import com.devarena.User.entity.User;
import com.devarena.User.repository.UserRepository;
import com.devarena.exception.ResourceNotFoundException;
import com.devarena.project.dto.ProjectRequestDto;
import com.devarena.project.dto.ProjectResponseDto;
import com.devarena.project.entity.Project;
import com.devarena.project.repository.ProjectRepository;

public class ProjectServiceImpl {

    private final UserRepository userRepo;
    private final ProjectRepository projectRepo;

    ProjectServiceImpl(UserRepository userRepo, ProjectRepository projectRepo){
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto request){
        Long ownerId = request.getOwnerId();
        User user = userRepo.getById(ownerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Owner with user id: " + ownerId + "not found"));
        if(user.getRole().equals("COMPANY")) 
            throw new UnsupportedOperationException("A company can't create a project");

        Project project = new Project.builder()
                        .ownerId(ownerId)
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .topic(request.getTopic())
                        .upVoteCount(0)
                        .createdAt(request.getCreatedAt())
                        .updatedAt(request.getUpdatedAt())
                        .build();



    }

    private ProjectResponseDto mapToResponse()
}
