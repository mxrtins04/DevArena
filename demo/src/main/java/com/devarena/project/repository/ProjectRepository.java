package com.devarena.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devarena.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByOwnerId(Long ownerId);

    List<Project> findByTopic(String topic, Pageable pageable);

    @Query("SELECT p FROM PROJECT p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', keyword, '%'))")
    List<Project> searchByTitleContaining(@Param("keyword") String keyword);

    @EntityGraph(attributePaths = {"owner"})
    Optional<Project> findProjectWithOwner(Long projectId);

}
