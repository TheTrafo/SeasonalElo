package org.training.service.impl;

import org.springframework.stereotype.Service;
import org.training.dto.team.TeamCreateRequest;
import org.training.dto.team.TeamResponse;
import org.training.dto.team.TeamUpdateRequest;
import org.training.mapper.TeamMapper;
import org.training.model.Team;
import org.training.repository.TeamRepository;
import org.training.service.TeamService;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final TeamMapper teamMapper;

    public TeamServiceImpl(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public TeamResponse createTeam(TeamCreateRequest teamCreateRequest) {
        Team team = teamRepository.save(teamMapper.mapToTeam(teamCreateRequest));
        return teamMapper.mapToTeamResponse(team);
    }

    @Override
    public List<TeamResponse> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teamMapper.mapToTeamListResponse(teams);
    }

    @Override
    public TeamResponse getTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        return teamMapper.mapToTeamResponse(team);
    }

    @Override
    public TeamResponse updateTeam(Long id, TeamUpdateRequest teamUpdateRequest) {
        Team teamToUpdate = teamRepository.findById(id).orElseThrow();
        teamToUpdate.setName(teamUpdateRequest.getName());
        teamToUpdate.setAbbreviation(teamUpdateRequest.getAbbreviation());
        Team saved = teamRepository.save(teamToUpdate);
        return teamMapper.mapToTeamResponse(saved);
    }

    @Override
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow();
        teamRepository.delete(team);
    }
}
