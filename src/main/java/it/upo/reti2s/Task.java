package it.upo.reti2s;

/**
 * Describe a Task with its properties.
 * @author <a href="mailto:luigi.derussis@uniupo.it">Luigi De Russis</a>
 * @version 1.1 (06/05/2018)
 */
public class Task {

    // the unique id of the task
    private int id;
    // the task content
    private String description;
    // whether the task is urgent or not
    private int urgent;


    /**
     * Task main constructor.
     *
     * @param id represents the task unique identifier
     * @param description the task content
     * @param urgent whether the task is urgent (1) or not (0)
     */
    public Task(int id, String description, int urgent) {
        this.id = id;
        this.description = description;
        this.urgent = urgent;
    }

    /**
     * Overloaded constructor. It set the newly created task as a non-urgent one.
     *
     * @param id represents the task unique identifier
     * @param description the task content
     */
    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.urgent = 0;
    }


    /*** Getters **/

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getUrgent() {
        return urgent;
    }

}
