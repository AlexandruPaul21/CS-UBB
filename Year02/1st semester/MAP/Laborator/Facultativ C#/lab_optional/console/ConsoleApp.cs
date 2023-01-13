using System.Globalization;
using lab_optional.domain;
using lab_optional.service;

namespace lab_optional.console;

public class ConsoleApp
{
    private Service _service;

    private delegate void ConsoleDelegate(String msg);

    private ConsoleDelegate printer;

    public ConsoleApp(Service service)
    {
        _service = service;
        printer = Console.WriteLine;
    }

    public void showMenu()
    {
        printer("Welcome to the app. These are the avaliable options. Please select one");
        printer("1. Print all the players from a specified team");
        printer("2. Print all the active players from a certain match");
        printer("3. Print all the matches between two given dates");
        printer("4. Print the score for a given match");
        printer("5. Exit");
    }

    private void ex1()
    {
        Console.WriteLine("Team id:");
        String id = Console.ReadLine();
        List<Player> jucatoriFromEchipa = _service.getPlayersFromTeam(id);
        Console.WriteLine("Team: " + _service.getTeamById(id));
        foreach (Player jucator in jucatoriFromEchipa)
        {
            Console.WriteLine(jucator.Id + ";" + jucator.Nume);
        }
    }
    
    private void ex2()
    {
        Console.WriteLine("Id meci:");
        String id = Console.ReadLine();
        List<ActivePlayer> jucatoriActiviFromMeci = _service.getActivePlayersFromMatch(id);
        Console.WriteLine("Meci: " + _service.getMatchById(id));
        foreach (ActivePlayer jucatorActiv in jucatoriActiviFromMeci)
        {
            Player player = _service.getPlayerById(jucatorActiv.IdPlayer);
            Console.WriteLine(player + ";" + jucatorActiv.Tip);
        }
    }
    private void ex3()
    {
        try
        {
            Console.WriteLine("Start date (dd/MM/yyyy):");
            String startDateString = Console.ReadLine();
            DateTime startDate = DateTime.ParseExact(startDateString, "dd/MM/yyyy", CultureInfo.InvariantCulture);
            Console.WriteLine("End date (dd/MM/yyyy):");
            String endDateString = Console.ReadLine();
            DateTime endDate = DateTime.ParseExact(endDateString, "dd/MM/yyyy", CultureInfo.InvariantCulture);
            List<Match> matchesFromPeriod = _service.getMatchesFromPeriod(startDate, endDate);
            foreach (Match match in matchesFromPeriod)
            {
                Console.WriteLine(match);
            }
        }
        catch (Exception exception)
        {
            Console.WriteLine(exception.Message);
        }

    }

    private void ex4()
    {
        Console.WriteLine("Match id:");
        String id = Console.ReadLine();
        Console.WriteLine("Match: " + _service.getMatchById(id));
        List<Int32> score = _service.getMatchScore(id);
        Console.WriteLine(score[0] + "-" + score[1]);
    }
    
    public void run()
    {
        showMenu();
        String line = Console.ReadLine();

        if (line == "1")
        {
            ex1();
        }
        else if (line == "2")
        {
            ex2();
        }
        else if (line == "3")
        {
            ex3();
        }
        else if (line == "4")
        {
            ex4();
        }
        else if (line == "5")
        {
            return;
        }
        else
        {
            Console.WriteLine("Invalid command");
        }
        
        run();
    }
}