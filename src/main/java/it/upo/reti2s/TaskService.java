package it.upo.reti2s;

import static spark.Spark.*;
import com.google.gson.Gson;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class TaskService {

    public static void main(String[] args) {
        // init
        Gson gson = new Gson();
        String baseURL = "/api/v1.0";
        TaskDao taskDao = new TaskDao();

        // get all the tasks
        get(baseURL + "/tasks", "application/json", (request, response) -> {
            response.type("application/json");
            response.status(200);

            List<Task> allTasks = taskDao.getAllTasks();
            Map<String, List<Task>> finalJson = new HashMap<>();
            finalJson.put("tasks", allTasks);

            return finalJson;
        }, gson::toJson);


        // get a single task
        get(baseURL + "/tasks/:id", "application/json", (request, response) -> {
            Task task = taskDao.getTask(Integer.valueOf(request.params(":id")));

            if(task==null)
                halt(404);

            Map<String, Task> finalJson = new HashMap<>();
            finalJson.put("task", task);
            response.status(200);
            response.type("application/json");

            return finalJson;
        }, gson::toJson);

        // add a new task
        post(baseURL + "/tasks", (request, response) -> {
            Map addRequest = gson.fromJson(request.body(), Map.class);

            if(addRequest!=null && addRequest.containsKey("description") && addRequest.containsKey("urgent")) {
                String description = String.valueOf(addRequest.get("description"));
                int urgent = ((Double) addRequest.get("urgent")).intValue();

                taskDao.addTask(description, urgent);

                response.status(201);
            }
            else {
                halt(403);
            }

            return "";
        });


    }

}
