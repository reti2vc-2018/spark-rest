package it.upo.reti2s;

import it.upo.reti2s.utils.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * The DAO for the {@code Task} class.
 * @author <a href="mailto:luigi.derussis@uniupo.it">Luigi De Russis</a>
 * @version 1.1 (06/05/2018)
 */
public class TaskDao {

    /**
     * Get all tasks from the DB
     * @return a list of task, or an empty list if no tasks are available
     */
    public List<Task> getAllTasks() {
        final String sql = "SELECT id, description, urgent FROM tasks";

        List<Task> tasks = new LinkedList<>();

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Task t = new Task(rs.getInt("id"), rs.getString("description"), rs.getInt("urgent"));
                tasks.add(t);
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Get a single task from the DB
     * @param id of the task to retrieve
     * @return the task, or null if not found
     */
    public Task getTask(int id)
    {
        Task task = null;
        final String sql = "SELECT description, urgent FROM tasks WHERE id = ?";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                task = new Task(id, rs.getString("description"), rs.getInt("urgent"));
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    /**
     * Add a new task into the DB
     * @param description the content of the new task
     * @param urgent whether the new task is urgent or not
     */
    public void addTask(String description, int urgent) {
        final String sql = "INSERT INTO tasks(description, urgent) VALUES (?, ?)";

        try {
            Connection conn = DBConnect.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, description);
            st.setInt(2, urgent);

            st.executeUpdate();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
