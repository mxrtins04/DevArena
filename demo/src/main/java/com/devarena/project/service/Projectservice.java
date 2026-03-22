package com.devarena.project.service;

import java.util.List;

import com.devarena.project.dto.ProjectRequestDto;
import com.devarena.project.dto.ProjectResponseDto;

public interface Projectservice {
    public ProjectResponseDto createProject(ProjectRequestDto);
    public ProjectResponseDto getProjectById(Long projectId);
    public List<ProjectResponseDto> getProjectByUser(Long ownerId);
    public List<ProjectResponseDto> getProjectByTopic(String topic, int page, int size);
    public List<ProjectResponseDto> searchProjects(String keyword);
}
