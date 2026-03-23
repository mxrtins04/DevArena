package com.devarena.project.service;

import java.util.List;

import com.devarena.project.dto.request.ProjectRequestDto;
import com.devarena.project.dto.response.ProjectResponseDto;

public interface Projectservice {
    public ProjectResponseDto createProject(ProjectRequestDto request);
    public ProjectResponseDto getProjectById(Long projectId);
    public List<ProjectResponseDto> getProjectByOwner(Long ownerId);
    public List<ProjectResponseDto> getProjectByTopic(String topic, int page, int size);
    public List<ProjectResponseDto> searchProjects(String keyword);
}
