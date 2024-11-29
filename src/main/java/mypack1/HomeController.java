package mypack1;

import model.Student;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import services.StudentDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/students/*")
public class HomeController extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();

    /**
     * @doGet method for getting all students
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            JSONArray studentsArray = new JSONArray();
            for (Student student : studentDAO.getAllStudents()) {
                studentsArray.put(createStudentJson(student));
            }
            out.print(studentsArray.toString());
        } else {
            try {
                int studentId = Integer.parseInt(pathInfo.substring(1));
                Student student = studentDAO.getStudent(studentId);
                if (student != null) {
                    out.print(createStudentJson(student).toString());
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"Student not found\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Invalid student ID\"}");
            }
        }
        out.flush();
    }

    /**
     * @doPost method for adding or registering newStudent
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            JSONObject requestBody = parseRequestBody(request);
            int studentId = studentDAO.getNextId();
            Student student = new Student(
                    studentId,  // autoId
                    (String) requestBody.get("name"),
                    (String) requestBody.get("email"),
                    (String) requestBody.get("schoolName"),
                    (String) requestBody.get("grade")
            );
            studentDAO.createStudent(student);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"message\": \"Student created successfully\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Invalid input\"}");
        }

        out.flush();
    }


    /**
     * @doPut method to update a new student
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Student ID is required\"}");
            return;
        }
        try {
            int studentId = Integer.parseInt(pathInfo.substring(1));
            JSONObject requestBody = parseRequestBody(request);
            Student student = new Student(
                    studentId,  //student ID,which comes from the path
                    (String) requestBody.get("name"),
                    (String) requestBody.get("email"),
                    (String) requestBody.get("schoolName"),
                    (String) requestBody.get("grade")
            );
            // Update the student using the DAO
            if (studentDAO.updateStudent(student)) {
                out.print("{\"message\": \"Student updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Student not found\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Invalid student ID\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Invalid input\"}");
        }
        out.flush();
    }

    /**
     * @doDelete method to delete a student by ID
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Student ID is required\"}");
            return;
        }
        try {
            int studentId = Integer.parseInt(pathInfo.substring(1));

            if (studentDAO.deleteStudent(studentId)) {
                out.print("{\"message\": \"Student deleted successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Student not found\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Invalid student ID\"}");
        }
        out.flush();
    }


    private JSONObject createStudentJson(Student student) {
        JSONObject studentJson = new JSONObject();
        studentJson.put("id", student.getId());
        studentJson.put("name", student.getName());
        studentJson.put("email", student.getEmail());
        studentJson.put("schoolName", student.getSchoolName());
        studentJson.put("grade", student.getGrade());

        JSONObject links = new JSONObject();
        links.put("self", "/SpringMVC_war_exploded//students/" + student.getId());
        links.put("update", "/SpringMVC_war_exploded/students/" + student.getId() + "/update");
        studentJson.put("_links", links);
        return studentJson;
    }

    private JSONObject parseRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return (JSONObject) org.json.simple.JSONValue.parse(sb.toString());
    }
}
