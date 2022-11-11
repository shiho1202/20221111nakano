package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserDAO;

@WebServlet("/LoginChk")
public class LoginChk extends HttpServlet{

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		// uooooo
		// セッション起動
		HttpSession session = req.getSession();

		// リクエストパラメータを取得
		String employeeNum = req.getParameter("employeeNum");
		String password = req.getParameter("password");

		// データベースへの接続
		UserDAO userDao = UserDAO.getInstance();

		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			// DB接続
			userDao.dbConnect();
			// ステートメント作成
			userDao.createSt();

			reMap = userDao.loginUser(employeeNum, password);
		}catch (Exception e ) {
			e.printStackTrace();
		} finally {
			// DB切断
			userDao.dbDiscon();
		}

		// セッションにパラメータをセット
		session.setAttribute("name", reMap.get("employeeName"));

		// 指定した画面へ遷移
		req.getRequestDispatcher("/portal.jsp").forward(req, res);
	}
}
