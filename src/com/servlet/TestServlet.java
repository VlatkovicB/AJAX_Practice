package com.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.DatabaseInteraction;
import com.google.gson.Gson;
import com.model.Product;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseInteraction db = new DatabaseInteraction();
	private Gson gson = new Gson();

	public TestServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Product> list = db.getAll();

		String products = gson.toJson(list);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(products);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		int category = Integer.parseInt(request.getParameter("category"));

		Product product = new Product(0, name, description, category);

		int id = db.insert(product);
		product.setId(id);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		db.delete(Integer.parseInt(id));
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletInputStream stream = request.getInputStream();

		Scanner scan = new Scanner(stream, "UTF-8");

		if (scan.hasNext()) {
			String product = scan.useDelimiter("\\A").next();

			System.out.println(product);

			Product p = gson.fromJson(product, Product.class);

			db.update(p);

//			String text = scan.next();
//			String[] niz = text.split("&");
//			for (String rec : niz) {
//				String[] deo = rec.split("=");
//
//				if (deo[0].equals("name")) {
//					name = deo[1];
//				}
//				if (deo[0].equals("category")) {
//					category = Integer.parseInt(deo[1]);
//				}
//				if (deo[0].equals("id")) {
//					id = Integer.parseInt(deo[1]);
//				}
//				if (deo[0].equals("description")) {
//					description = deo[1];
//				}
//
//			}
		}
//		System.out.println(name + category + id + description);

		scan.close();

//		String name = request.getParameter("name");
//		String description = request.getParameter("description");
//		String category = request.getParameter("category");
//		String id = request.getParameter("id");
//		System.out.println(category);
//		System.out.println(id);
//		Product product = new Product(id, name, description, category);
//
////		db.update(product);
	}

}
