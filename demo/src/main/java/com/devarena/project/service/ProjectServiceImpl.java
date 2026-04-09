package com.devarena.project.service;

import com.devarena.user.entity.User;
import com.devarena.user.enums.UserRole;
import com.devarena.user.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

import com.devarena.project.repository.ProjectRepository;
import com.devarena.project.enums.ProjectStatus;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devarena.exception.ResourceNotFoundException;
import com.devarena.project.dto.request.ProjectRequestDto;
import com.devarena.project.dto.response.ProjectResponseDto;
import com.devarena.project.dto.response.ProjectSummaryResponseDto;
import com.devarena.project.entity.Project;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final UserRepository userRepo;
    private final ProjectRepository projectRepo;

    public ProjectServiceImpl(UserRepository userRepo, ProjectRepository projectRepo) {
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
    }

    @Override
    @Transactional(rollbackFor= Exception.class)
    public ProjectResponseDto createProject(ProjectRequestDto request) {
        UUID ownerId = request.getOwnerId();
        User owner = userRepo.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with user id: " + ownerId + " not found"));

        if (owner.getRole() == UserRole.COMPANY) {
            throw new UnsupportedOperationException("A company can't create a project");
        }

        Project project = Project.builder()
                .owner(owner)
                .title(request.getTitle())
                .description(request.getDescription())
                .repositoryUrl(request.getRepositoryUrl())
                .status(ProjectStatus.DRAFT)
                .build();

        Project savedProject = projectRepo.save(project);
        return mapToResponse(savedProject);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponseDto getProjectById(UUID projectId) {
        Project project = projectRepo.findProjectWithOwner(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("No project found with id " + projectId));
        return mapToResponse(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectSummaryResponseDto> getProjectByOwner(UUID ownerId) {
        User owner = userRepo.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with user id: " + ownerId + " not found"));
        return projectRepo.findByOwnerId(ownerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectSummaryResponseDto> getProjectsByTopic(String topic, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                org.springframework.data.domain.Sort.by("createdAt").descending());
        return projectRepo.findByTopic(topic, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProjectSummaryResponseDto> searchProjects(String keyword) {
        return projectRepo.searchByTitleContaining(keyword);
    }

    private ProjectResponseDto mapToResponse(Project project) {
        return ProjectResponseDto.builder()
                .projectId(project.getId())
                .ownerUsername(project.getOwner().getUsername())
                .title(project.getTitle())
                .description(project.getDescription())
                .repositoryUrl(project.getRepositoryUrl())
                .voteCount(project.getVoteCount())
                .status(project.getStatus())
                .createdAt(project.getCreatedAt())
                .build();
    }

}
