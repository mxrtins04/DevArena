package com.devarena.project.service;

import com.devarena.user.entity.User;
import com.devarena.user.enums.UserRole;
import com.devarena.user.repository.UserRepository;
import com.devarena.exception.ResourceNotFoundException;
import com.devarena.project.dto.ProjectRequestDto;
import com.devarena.project.dto.ProjectResponseDto;
import com.devarena.project.entity.Project;
import com.devarena.project.repository.ProjectRepository;

public class ProjectServiceImpl implements Projectservice {

    private final UserRepository userRepo;
    private final ProjectRepository projectRepo;

    ProjectServiceImpl(UserRepository userRepo, ProjectRepository projectRepo) {
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto request) {
        Long ownerId = request.getOwnerId();
        User owner = userRepo.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with user id: " + ownerId + " not found"));

        if (owner.getRole() == UserRole.COMPANY) {
            throw new UnsupportedOperationException("A company can't create a project");
        }

        Project project = Project.builder()
                .owner(owner)
                .title(request.getTitle())
                .description(request.getDescription())
                .topic(request.getTopic())
                .build();

        Project savedProject = projectRepo.save(project);
        return mapToResponse(savedProject);
    }

    private ProjectResponseDto mapToResponse(Project project) {
        return ProjectResponseDto.builder()
                .projectId(project.getProjectId())
                .ownerId(project.getOwner().getId())
                .title(project.getTitle())
                .topic(project.getTopic())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .build();
    }
}
