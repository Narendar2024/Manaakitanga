import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("contact");
        String password = request.getParameter("pass");

        Connection con = null;
        PreparedStatement pst = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourDB", "root", "password");

            String query = "INSERT INTO users (name, email, mobile, password) VALUES (?, ?, ?, ?)";
            pst = con.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, mobile);
            pst.setString(4, password);

            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                request.setAttribute("status", "success");
            } else {
                request.setAttribute("status", "failed");
            }

            request.getRequestDispatcher("register.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("status", "failed");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
