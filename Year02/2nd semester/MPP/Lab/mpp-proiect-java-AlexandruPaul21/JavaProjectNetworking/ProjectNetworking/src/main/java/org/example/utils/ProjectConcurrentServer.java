package org.example.utils;

import org.example.IProjectServices;
import org.example.protocol.ProjectClientWorker;

import java.net.Socket;

public class ProjectConcurrentServer extends AbstractConcurrentServer{
    private IProjectServices projectServices;

    public ProjectConcurrentServer(int port, IProjectServices projectServices) {
        super(port);
        this.projectServices = projectServices;
        System.out.println("Created concurrent server");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ProjectClientWorker worker = new ProjectClientWorker(projectServices, client);

        Thread th = new Thread(worker);
        return th;
    }

    @Override
    public void stop() {
        System.out.println("Stopping concurrent server");
    }
}
