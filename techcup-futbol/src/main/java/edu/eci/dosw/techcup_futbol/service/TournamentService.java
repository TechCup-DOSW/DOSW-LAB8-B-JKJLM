package edu.eci.dosw.techcup_futbol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

import edu.eci.dosw.techcup_futbol.dtos.TournamentCreateDTO;
import edu.eci.dosw.techcup_futbol.entity.TournamentEntity;
import edu.eci.dosw.techcup_futbol.model.Competences.Tournament;
import edu.eci.dosw.techcup_futbol.model.UsersAndSecurity.Organizer;
import edu.eci.dosw.techcup_futbol.repository.TournamentRepository;

@Service
public class TournamentService {

    private static final Logger logger = LoggerFactory.getLogger(TournamentService.class);

    @Autowired
    private TournamentRepository tournamentRepository;

    public Tournament createTournament(TournamentCreateDTO dto) {
        logger.info("Starting tournament creation with name: {}", dto.getName());

        Organizer organizer = new Organizer(1, "Carlos Admin", "carlos@eci.edu", "password123");

        Tournament newTournament = organizer.createTournament(
                dto.getName(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getMaxTeams(),
                dto.getPlayersPerTeam(),
                dto.getCostPerTeam()
        );

        TournamentEntity saved = tournamentRepository.save(toEntity(newTournament));
        logger.info("Tournament created successfully with id: {}", saved.getId());

        return toDomain(saved);
    }

    public List<Tournament> getAllTournaments() {
        logger.info("Fetching all tournaments");
        return tournamentRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    public Tournament getTournamentById(Long id) {
        logger.info("Fetching tournament with id: {}", id);

        return tournamentRepository.findById(id)
                .map(this::toDomain)
                .orElseThrow(() -> {
                    logger.error("Tournament not found with id: {}", id);
                    return new NoSuchElementException("No tournament found with id: " + id);
                });
    }

    public Tournament updateTournament(Long id, TournamentCreateDTO dto) {
        logger.info("Updating tournament with id: {}", id);

        Tournament existing = getTournamentById(id);

        existing.setName(dto.getName());
        existing.setTournamentDates(dto.getStartDate(), dto.getEndDate());
        existing.setMaxTeams(dto.getMaxTeams());
        existing.setPlayersPerTeam(dto.getPlayersPerTeam());
        existing.setCostPerTeam(dto.getCostPerTeam());

        TournamentEntity saved = tournamentRepository.save(toEntity(existing));
        logger.info("Tournament updated successfully with id: {}", id);

        return toDomain(saved);
    }

    public void deleteTournament(Long id) {
        logger.info("Deleting tournament with id: {}", id);

        if (!tournamentRepository.existsById(id)) {
            logger.error("Cannot delete tournament. Tournament does not exist with id: {}", id);
            throw new NoSuchElementException("Cannot delete tournament. Tournament does not exist with id: " + id);
        }

        tournamentRepository.deleteById(id);
        logger.info("Tournament deleted successfully with id: {}", id);
    }

    private TournamentEntity toEntity(Tournament tournament) {
        TournamentEntity entity = new TournamentEntity();
        entity.setId(tournament.getId());
        entity.setName(tournament.getName());
        entity.setStartDate(tournament.getStartDate());
        entity.setEndDate(tournament.getEndDate());
        entity.setMaxTeams(tournament.getMaxTeams());
        entity.setPlayersPerTeam(tournament.getPlayersPerTeam());
        entity.setCostPerTeam(tournament.getCostPerTeam());
        entity.setStatus(tournament.getStatus());
        entity.setRules(tournament.getRules());
        return entity;
    }

    private Tournament toDomain(TournamentEntity entity) {
        return new Tournament(
                entity.getId(),
                entity.getName(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getMaxTeams(),
                entity.getPlayersPerTeam(),
                entity.getCostPerTeam(),
                entity.getStatus(),
                entity.getRules()
        );
    }
}
