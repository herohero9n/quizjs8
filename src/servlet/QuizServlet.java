package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Quiz;
import model.QuizLogic;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuizServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		//問題の初期値設定
		int quizNum = 1;
		if (action == null) {
			//
			int score = 0;
			session.setAttribute("score", score);
		} else if (action.equals("done")) {
			//問題番号をセッションから取り出す
			quizNum = (int) session.getAttribute("quizNum");
			//加算
			quizNum++;
		}

		//問題をDBから取得

		QuizLogic quizLogic = new QuizLogic();
		Quiz quiz = quizLogic.execute(quizNum);

		if (quiz != null) {
			//問題をセッションスコープに
			session.setAttribute("quiz", quiz);
			//問題番号をセッションスコープに
			session.setAttribute("quizNum", quizNum);

			//フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/quiz.jsp");
			dispatcher.forward(request, response);
		} else {
			//			session.invalidate();
			//終了ページへフォワード
			RequestDispatcher dispatcherEnd = request.getRequestDispatcher("/WEB-INF/jsp/end.jsp");
			dispatcherEnd.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//パラメータ取得
		request.setCharacterEncoding("UTF-8");
		String choice = request.getParameter("choice");
		String forwardPass = null;
		HttpSession session = request.getSession();
		//セッションスコープから問題の解答を取得
		Quiz quiz = (Quiz) session.getAttribute("quiz");

		String quizString = String.valueOf(quiz.getAnswer());

		//最初の問題かそれ以外かでscoreの初期値設定
		int score = 0;
		int quizNum = (int) session.getAttribute("quizNum");
		if (quizNum != 1) {
			score = (int) session.getAttribute("score");
		}

		//正誤判定
		if (choice.equals(quizString)) {
			//正解をカウント
			score++;
			System.out.println(score);
			session.setAttribute("score", score);
			forwardPass = "/WEB-INF/jsp/right.jsp";
		} else {
			forwardPass = "/WEB-INF/jsp/wrong.jsp";
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPass);
		dispatcher.forward(request, response);
	}
}
