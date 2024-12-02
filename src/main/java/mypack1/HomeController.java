package mypack1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Student;
import services.StudentDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/students")
public class HomeController extends HttpServlet {
    private final StudentDAO studentDAO = new StudentDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @doGet method to get the all and individual students
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
            ArrayNode studentsArray = objectMapper.createArrayNode();
            for (Student student : studentDAO.getAllStudents()) {
                studentsArray.add(createStudentJson(student));
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
     * @doPost method to post or add a student into the db (hashmap)
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
            ObjectNode requestBody = parseRequestBody(request);
            int studentId = studentDAO.getNextId();
            Student student = new Student(
                    studentId,
                    requestBody.get("name").asText(),
                    requestBody.get("email").asText(),
                    requestBody.get("schoolName").asText(),
                    requestBody.get("grade").asText()
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
     * @doPut method used to update the student info
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
            ObjectNode requestBody = parseRequestBody(request);
            Student student = new Student(
                    studentId,
                    requestBody.get("name").asText(),
                    requestBody.get("email").asText(),
                    requestBody.get("schoolName").asText(),
                    requestBody.get("grade").asText()
            );

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
     * @delete method deletes student from the db(hashmap)
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


    /**
     * @createStudentJson is a mutable JSON Library from Jackson that is use to populate the Json response
     * @param student
     * @return
     */
    private ObjectNode createStudentJson(Student student) {
        ObjectNode studentJson = objectMapper.createObjectNode();
        studentJson.put("id", student.getId());
        studentJson.put("name", student.getName());
        studentJson.put("email", student.getEmail());
        studentJson.put("schoolName", student.getSchoolName());
        studentJson.put("class", student.getGrade());

        ObjectNode links = objectMapper.createObjectNode();
        links.put("operations", "/SpringMVC/students/" + student.getId());
        studentJson.set("_links", links);

        return studentJson;
    }

    private ObjectNode parseRequestBody(HttpServletRequest request) throws IOException {
        return (ObjectNode) objectMapper.readTree(request.getReader());
    }
}
