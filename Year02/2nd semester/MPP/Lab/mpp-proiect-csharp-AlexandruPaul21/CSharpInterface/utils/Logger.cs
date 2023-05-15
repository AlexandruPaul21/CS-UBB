namespace CSharpInterface.utils;

public class Logger
{
    public void Info(string msg)
    {
        var date = DateTime.Now;
        Console.WriteLine("{0} {1} {2} {3} TRACE - {4}", date.Year, date.Month, date.Day, date.TimeOfDay, msg);
    }

    public void Error(string msg)
    {
        var date = DateTime.Now;
        Console.WriteLine("{0} {1} {2} {3} ERROR - {4}", date.Year, date.Month, date.Day, date.TimeOfDay, msg);
    }
}