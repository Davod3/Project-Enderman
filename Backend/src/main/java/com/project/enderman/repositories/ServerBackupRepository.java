package com.project.enderman.repositories;

import com.project.enderman.entities.ServerBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerBackupRepository extends JpaRepository<ServerBackup, Long> {
}
