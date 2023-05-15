using networking;
using services;

namespace client;

static class StartChatClient
{
    [STAThread]
    static void Main()
    {
        Application.EnableVisualStyles();
        Application.SetCompatibleTextRenderingDefault(false);


        //IChatServer server=new ChatServerMock();          
        IProjectServices server = new ProjectServerProxy("127.0.0.1", 5555);
        ProjectClientCtrl ctrl = new ProjectClientCtrl(server);
        login win = new login(ctrl);
        Application.Run(win);
    }
}