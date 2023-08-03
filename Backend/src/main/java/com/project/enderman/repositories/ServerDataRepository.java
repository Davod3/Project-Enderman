package com.project.enderman.repositories;

import com.project.enderman.entities.ServerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServerDataRepository extends JpaRepository<ServerData, Long> {

    @Query("SELECT s.port FROM ServerData s ")
    public List<String> getUsedServerPorts();

    @Query("SELECT s.rconPort FROM ServerData s ")
    public List<String> getUsedRconPorts();

}
