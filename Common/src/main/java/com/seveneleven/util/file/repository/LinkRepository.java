package com.seveneleven.util.file.repository;

import com.seveneleven.entity.file.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
