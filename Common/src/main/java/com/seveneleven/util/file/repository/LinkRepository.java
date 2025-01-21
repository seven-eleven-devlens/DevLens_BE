package com.seveneleven.util.file.repository;

import com.seveneleven.entity.file.Link;
import com.seveneleven.entity.file.constant.LinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByCategoryAndReferenceId(LinkCategory category, Long referenceId);
    List<Link> findAllByCategoryAndReferenceId(LinkCategory linkCategory, Long referenceId);
}
