package com.devarena.project.service;

import com.devarena.user.entity.User;
import com.devarena.user.enums.UserRole;
import com.devarena.user.repository.UserRepository;
import com.devarena.project.repository.ProjectRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.devarena.exception.ResourceNotFoundException;
import com.devarena.project.dto.ProjectRequestDto;
import com.devarena.project.dto.ProjectResponseDto;
import com.devarena.project.entity.Project;

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

    public ProjectResponseDto getProjectById(Long projectId) {
        Project project = projectRepo.findProjectWithOwner(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("No project found with id " + projectId));
        return mapToResponse(project);
    }

    public List<ProjectResponseDto> getProjectByOwner(Long ownerId) {
        if (projectRepo.findByOwnerId(ownerId) == null)
            throw new ResourceNotFoundException(null);
        List<Project> projects = projectRepo.findByOwnerId(ownerId);
        return projects.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponseDto> getProjectsByTopic(String topic, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                org.springframework.data.domain.Sort.by("createdAt").descending());
        List<Project> projectPage = projectRepo.findByTopic(topic, pageable);
        return projectPage.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectResponseDto> searchProjects(String keyword) {
        List<Project> projects = projectRepo.searchByTitleContaining(keyword);
        return projects.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
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
