package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.model.Product;

public class DatabaseInteraction {
	private static final String URL = "jdbc:mysql://localhost:3306/test_page?autoReconnect=true&useSSL=false";
	private static final String UN = "root";
	private static final String PW = "MyNewPass";
	private Connection con;
	private PreparedStatement prepStmt;
	private ResultSet rs;

	/**
	 * Singleton
	 */
	public DatabaseInteraction() {
	}

	/**
	 * private constructor
	 */

	/**
	 * open connection if it's not open already
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void openConnection() throws SQLException, ClassNotFoundException {
		if (con == null) {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL, UN, PW);
		}
	}

	/**
	 * close resources
	 * 
	 * @throws SQLException
	 */
	private void closeResources() throws SQLException {
		if (rs != null && !rs.isClosed()) {
			rs.close();
		}
		if (prepStmt != null && !prepStmt.isClosed()) {
			prepStmt.close();
		}
//		if (con != null && !con.isClosed()) {
//			con.close();
//		}
	}

	/**
	 * Get all in form of a List of products
	 */
	public List<Product> getAll() {
		List<Product> products = new ArrayList<Product>();

		try {
			openConnection();
			prepStmt = con.prepareStatement("SELECT * FROM product");

			rs = prepStmt.executeQuery();

			while (rs.next()) {
				Product product = new Product((int) rs.getObject(1), rs.getObject(2).toString(),
						rs.getObject(3).toString(), (int) rs.getObject(4));
				products.add(product);
			}

			closeResources();
		} catch (Exception x) {
			System.out.println(x.getMessage());
		} finally {
		}

		return products;
	}

	/**
	 * Inserts a product into database
	 */
	public int insert(Product product) {
		int result = 0;
		try {
			openConnection();
			prepStmt = con.prepareStatement("INSERT INTO product (name, description, category) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			prepStmt.setObject(1, product.getName());
			prepStmt.setObject(2, product.getDescription());
			prepStmt.setObject(3, product.getCategory());

			prepStmt.execute();
			rs = prepStmt.getGeneratedKeys();
			rs.next();
			result = rs.getInt(1);
			closeResources();
		} catch (Exception x) {
			System.out.println(x.getMessage());
		}
		return result;
	}

	/**
	 * Get a single product with request ID.
	 */
	public Product get(int id) {
		Product product = null;
		try {
			openConnection();

			prepStmt = con.prepareStatement("SELECT * FROM product WHERE id =" + id);

			rs = prepStmt.executeQuery();

			rs.next();
			product = new Product((int) rs.getObject(1), rs.getObject(2).toString(), rs.getObject(3).toString(),
					(int) rs.getObject(4));

			closeResources();
		} catch (Exception x) {
			System.out.println(x.getMessage());
		}

		return product;
	}

	/**
	 * Provide a product and update it in database with the same product ID.
	 */
	public boolean update(Product product) {
		boolean result = false;
		try {
			openConnection();
			prepStmt = con.prepareStatement("UPDATE product SET name = ?, description = ?, category = ? WHERE id = ? ");

			prepStmt.setObject(1, product.getName());
			prepStmt.setObject(2, product.getDescription());
			prepStmt.setObject(3, product.getCategory());
			prepStmt.setObject(4, product.getId());

			result = prepStmt.executeUpdate() == 1;

			closeResources();
		} catch (Exception x) {
			System.out.println(x.getMessage());
		}

		return result;
	}

	/**
	 * Deletes a product from database.
	 */
	public boolean delete(int id) {
		boolean result = false;
		try {
			openConnection();
			prepStmt = con.prepareStatement("DELETE FROM product WHERE id = ?");
			prepStmt.setObject(1, id);

			result = prepStmt.executeUpdate() == 1;
			closeResources();
		} catch (Exception x) {
			System.out.println(x.getMessage());
		}

		return result;
	}

	public List<Product> getCategory(int categoryId) {
		List<Product> products = new ArrayList<>();
		try {
			openConnection();
			String query = "SELECT * FROM product WHERE category = ? ";
			prepStmt = con.prepareStatement(query);
			prepStmt.setObject(1, categoryId);
			rs = prepStmt.executeQuery();

			while (rs.next()) {
				Product product = new Product((int) rs.getObject(1), rs.getObject(2).toString(),
						rs.getObject(3).toString(), (int) rs.getObject(4));
				products.add(product);
			}
			closeResources();
		} catch (Exception x) {
			System.out.println(x.getMessage());
		}
		return products;
	}

}
