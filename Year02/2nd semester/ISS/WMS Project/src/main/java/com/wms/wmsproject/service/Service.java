package com.wms.wmsproject.service;

import com.wms.wmsproject.model.Task;
import com.wms.wmsproject.repository.TaskRepository;
import com.wms.wmsproject.utils.enums.LoginResponseType;
import com.wms.wmsproject.utils.observer.Observable;
import com.wms.wmsproject.utils.responses.LoginResponse;
import com.wms.wmsproject.model.Admin;
import com.wms.wmsproject.model.Worker;
import com.wms.wmsproject.repository.AdminRepository;
import com.wms.wmsproject.repository.WorkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service extends Observable {
    public Service() {}

    private AdminRepository adminRepository;
    private WorkerRepository workerRepository;
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Autowired
    public void setWorkerRepository(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public LoginResponse login(String username, String password) {
        LoginResponse response = new LoginResponse(LoginResponseType.FAILED, null);
        Admin adminFound = adminRepository.findByIdAndPassword(username, password);
        if (adminFound != null) {
            response.setType(LoginResponseType.ADMIN);
            response.setBody(adminFound);
        } else {
            Worker workerFound = workerRepository.findByIdAndPassword(username, password);
            if (workerFound != null) {
                response.setType(LoginResponseType.WORKER);
                response.setBody(workerFound);
            }
        }
        notifyObservers();

        return response;
    }

    public Task addTask(Task task) {
        Task task1 = taskRepository.save(task);
        notifyObservers();
        return task1;
    }

    @Transactional
    public void startWorking(String id) {
        workerRepository.startWorking(id);
        notifyObservers();
    }

    @Transactional
    public void stopWorking(String id) {
        workerRepository.stopWorking(id);
        notifyObservers();
    }

    @Transactional
    public void markAsDone(Long id) {
        taskRepository.markAsDone(id);
        notifyObservers();
    }

    public List<Worker> getAllAvailableWorkers() {
        return workerRepository.findAllAvailableWorkers();
    }

    public Worker addWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public void deleteWorker(String id) {
        workerRepository.deleteById(id);
    }

    public void updateWorker(Worker worker) {
        workerRepository.save(worker);
    }

    public List<Task> getTasksForWorker(String id) {
        return taskRepository.findAllByWorkerId(id);
    }

    public Long getIdForTask() {
        Optional<Task> optional =  taskRepository.findAll()
                .stream()
                .reduce((first, second) -> {
                    if (first.getId() > second.getId()) {
                        return first;
                    } else {
                        return second;
                    }
                });
        return optional.map(task -> task.getId() + 1).orElse(1L);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
