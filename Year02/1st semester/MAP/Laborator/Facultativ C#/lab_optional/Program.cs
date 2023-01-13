// See https://aka.ms/new-console-template for more information

using lab_optional.domain;
using lab_optional.repository;
using lab_optional.service;
using lab_optional.console;

Repository<String, Team> teamFileRepository = new TeamFileRepository("/Users/alexandrusirbu/RiderProjects/lab_optional/lab_optional/resources/teams.txt");
Repository<String, Player> playersFileRepository = new PlayersFileRepository("/Users/alexandrusirbu/RiderProjects/lab_optional/lab_optional/resources/players.txt");
Repository<String, Match> matchFileRepository = new MatchFileRepository("/Users/alexandrusirbu/RiderProjects/lab_optional/lab_optional/resources/matches.txt");
Repository<String, ActivePlayer> activePlayersFileRepository = new ActivePlayersFileRepository("/Users/alexandrusirbu/RiderProjects/lab_optional/lab_optional/resources/activePlayers.txt");

Service service = new Service(teamFileRepository, playersFileRepository, matchFileRepository, activePlayersFileRepository);

ConsoleApp console = new ConsoleApp(service);

console.run();