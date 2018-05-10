package it.upo.reti2s;

import static spark.Spark.*;
import com.google.gson.Gson;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * The main REST service for handling tasks: it starts the server and handles all the HTTP requests.
 * @author <a href="mailto:luigi.derussis@uniupo.it">Luigi De Russis</a>
 * @version 1.1 (10/05/2018)
 */
public class TaskService {

    public static void main(String[] args) {
        // init
        Gson gson = new Gson();
        String baseURL = "/api/v1.0";
        TaskDao taskDao = new TaskDao();

        // get all the tasks
        get(baseURL + "/tasks", "application/json", (request, response) -> {
            // set a proper response code and type
            response.type("application/json");
            response.status(200);

            // get all tasks from the DB
            List<Task> allTasks = taskDao.getAllTasks();
            // prepare the JSON-related structure to return
            Map<String, List<Task>> finalJson = new HashMap<>();
            finalJson.put("tasks", allTasks);

            return finalJson;
        }, gson::toJson);


        // get a single task
        get(baseURL + "/tasks/:id", "application/json", (request, response) -> {
            // get the id from the URL
            Task task = taskDao.getTask(Integer.valueOf(request.params(":id")));

            // no task? 404!
            if(task==null)
                halt(404);

            // prepare the JSON-related structure to return
            // and the suitable HTTP response code and type
            Map<String, Task> finalJson = new HashMap<>();
            finalJson.put("task", task);
            response.status(200);
            response.type("application/json");

            return finalJson;
        }, gson::toJson);

        // add a new task
        post(baseURL + "/tasks", (request, response) -> {
            // get the body of the HTTP request
            Map addRequest = gson.fromJson(request.body(), Map.class);

            // check whether everything is in place
            if(addRequest!=null && addRequest.containsKey("description") && addRequest.containsKey("urgent")) {
                String description = String.valueOf(addRequest.get("description"));
                // gson convert JSON num in double, but we need an int
                int urgent = ((Double) addRequest.get("urgent")).intValue();

                // add the task into the DB
                taskDao.addTask(description, urgent);

                // if success, prepare a suitable HTTP response code
                response.status(201);
            }
            else {
                halt(403);
            }

            return "";
        });


    }

}
