/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO.AccountDAO;
import model.DAO.CategoryDAO;
import model.DAO.ProductDAO;
import model.DTO.Account;
import model.DTO.Category;
import model.DTO.Product;

/**
 *
 * @author beu29
 */
public class StaffController extends HttpServlet {

    private final String STAFF_HOME = "/view/private/staff/staffHome.jsp";
    private final String ADD_CATEGORY = "/view/private/staff/AddCategory.jsp";
    private final String CATEGORIES_LIST = "/view/private/staff/CategoriesList.jsp";
    private final String UPDATE_CATEGORY = "/view/private/staff/UpdateCategory.jsp";
    private final String ADD_PRODUCT = "/view/private/staff/AddProduct.jsp";
    private final String PRODUCTS_LIST = "/view/private/staff/ProductsList.jsp";
    private final String UPDATE_PRODUCT = "/view/private/staff/UpdateProduct.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void setCategoriesList(HttpServletRequest request) {
        List<Category> ls = new CategoryDAO().listAll();
        request.setAttribute("categoriesList", ls);
    }

    protected void setProductsList(HttpServletRequest request) {
        List<Product> ls = new ProductDAO().listAll();
        request.setAttribute("productsList", ls);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        String action = request.getParameter("action");
        if (action == null) {
            action = "home";
        }
        
        List<Category> catLs = new CategoryDAO().listAll();
        request.setAttribute("categoriesList", catLs);

        switch (action) {
            case "home":
                request.getRequestDispatcher(STAFF_HOME).forward(request, response);
                break;
            case "addCategory":
                request.getRequestDispatcher(ADD_CATEGORY).forward(request, response);
                break;
            case "categoriesList":
                setCategoriesList(request);
                request.getRequestDispatcher(CATEGORIES_LIST).forward(request, response);
                break;
            case "deleteCategory":
                String delCatId = request.getParameter("deleteId");
                Category delCat = new CategoryDAO().getObjectById(delCatId);
                boolean kq = new CategoryDAO().deleteRec(delCat);
                if (kq) {
                    setCategoriesList(request);
                    request.getRequestDispatcher(CATEGORIES_LIST).forward(request, response);
                } else {
                    request.setAttribute("ErrorMessage", "There's something wrong. Fail to perform task !!!");
                    setCategoriesList(request);
                    request.getRequestDispatcher(CATEGORIES_LIST).forward(request, response);
                }
                break;
            case "updateCategory":
                String updCatId = request.getParameter("updateId");
                Category updCat = new CategoryDAO().getObjectById(updCatId);
                request.setAttribute("cat", updCat);
                request.getRequestDispatcher(UPDATE_CATEGORY).forward(request, response);
                break;
            case "addProduct":
                request.getRequestDispatcher(ADD_PRODUCT).forward(request, response);
                break;
            case "productsList":
                setProductsList(request);
                request.getRequestDispatcher(PRODUCTS_LIST).forward(request, response);
                break;
            case "deleteProduct":
                String productId = request.getParameter("deleteId");
                Product pro = new ProductDAO().getObjectById(productId);
                kq = new ProductDAO().deleteRec(pro);
                if (kq) {
                    setProductsList(request);
                    request.getRequestDispatcher(PRODUCTS_LIST).forward(request, response);
                } else {
                    request.setAttribute("ErrorMessage", "There's something wrong. Fail to perform task !!!");
                    setProductsList(request);
                    request.getRequestDispatcher(PRODUCTS_LIST).forward(request, response);
                }
                break;
            case "updateProduct":
                productId = request.getParameter("updateId");
                pro = new ProductDAO().getObjectById(productId);
                request.setAttribute("pro", pro);
                request.getRequestDispatcher(UPDATE_PRODUCT).forward(request, response);
                break;
            case "sortProduct":
                String priceRange = request.getParameter("priceRange");
                String min = request.getParameter("minPrice");
                int minPrice = (min != null && !min.isEmpty()) ? Integer.parseInt(min) : Integer.MIN_VALUE;
                String max = request.getParameter("maxPrice");
                int maxPrice = (max != null && !max.isEmpty()) ? Integer.parseInt(max) : Integer.MAX_VALUE;
                String productName = request.getParameter("productName");
                String hasDiscount = request.getParameter("hasDiscount");
                String cat = request.getParameter("category");
                int catId = (cat != null && !cat.isEmpty()) ? Integer.parseInt(cat) : 0;

                List<Product> sortProdList = new ProductDAO().sortProduct(priceRange, minPrice, maxPrice, productName, hasDiscount, catId);

                request.setAttribute("productsList", sortProdList);
                request.getRequestDispatcher(PRODUCTS_LIST).forward(request, response);
                break;
            case "sortCategory":
                String id = request.getParameter("catId");
                String name = request.getParameter("catName");
                List<Category> sortCatList = new CategoryDAO().sortCategory(id, name);
                request.setAttribute("categoriesList", sortCatList);
                request.getRequestDispatcher(CATEGORIES_LIST).forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action: " + action);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        String action = request.getParameter("action");
        if (action == null) {
            action = "home";
        }
        switch (action) {
            case "addCategory":
                String categoryName = request.getParameter("categoryName");
                String memo = request.getParameter("memo");

                Category addCat = new Category(0, categoryName, memo);
                boolean kq = new CategoryDAO().insertRec(addCat);

                if (kq) {
                    setCategoriesList(request);
                    request.getRequestDispatcher(CATEGORIES_LIST).forward(request, response);
                } else {
                    request.setAttribute("ErrorMessage", "There's something wrong. Fail to perform task !!!");
                    setCategoriesList(request);
                    request.getRequestDispatcher(CATEGORIES_LIST).forward(request, response);
                }
                break;
            case "updateCategory":
                int typeId = Integer.parseInt(request.getParameter("typeId"));
                categoryName = request.getParameter("categoryName");
                memo = request.getParameter("memo");

                Category cat = new Category(typeId, categoryName, memo);
                kq = new CategoryDAO().updateRec(cat);

                if (kq) {
                    setCategoriesList(request);
                    request.getRequestDispatcher(CATEGORIES_LIST).forward(request, response);
                } else {
                    request.setAttribute("ErrorMessage", "There's something wrong. Fail to perform task !!!");
                    setCategoriesList(request);
                    request.getRequestDispatcher(CATEGORIES_LIST).forward(request, response);
                }
                break;
            case "addProduct":
                String productId = request.getParameter("productId");
                String productName = request.getParameter("productName");
                String productImage = request.getParameter("productImage");
                String brief = request.getParameter("brief");
                Date postedDate = Date.valueOf(request.getParameter("postedDate"));
                typeId = Integer.parseInt(request.getParameter("typeID"));
                cat = new CategoryDAO().getObjectById(String.valueOf(typeId));
                String account = request.getParameter("account");
                Account acc = new AccountDAO().getObjectById(account);
                String unit = request.getParameter("unit");
                int price = Integer.parseInt(request.getParameter("price"));
                int discount = Integer.parseInt(request.getParameter("discount"));

                Product pro = new Product(productId, productName, productImage, brief, postedDate, cat, acc, unit, price, discount);
                kq = new ProductDAO().insertRec(pro);

                if (kq) {
                    setProductsList(request);
                    request.getRequestDispatcher(PRODUCTS_LIST).forward(request, response);
                } else {
                    request.setAttribute("ErrorMessage", "There's something wrong. Fail to perform task !!!");
                    setProductsList(request);
                    request.getRequestDispatcher(PRODUCTS_LIST).forward(request, response);
                }
                break;
            case "updateProduct":
                productId = request.getParameter("productId");
                productName = request.getParameter("productName");
                productImage = request.getParameter("productImage");
                brief = request.getParameter("brief");
                postedDate = Date.valueOf(request.getParameter("postedDate"));
                typeId = Integer.parseInt(request.getParameter("typeID"));
                cat = new CategoryDAO().getObjectById(String.valueOf(typeId));
                account = request.getParameter("account");
                acc = new AccountDAO().getObjectById(account);
                unit = request.getParameter("unit");
                price = Integer.parseInt(request.getParameter("price"));
                discount = Integer.parseInt(request.getParameter("discount"));

                pro = new Product(productId, productName, productImage, brief, postedDate, cat, acc, unit, price, discount);
                kq = new ProductDAO().updateRec(pro);

                if (kq) {
                    setProductsList(request);
                    request.getRequestDispatcher(PRODUCTS_LIST).forward(request, response);
                } else {
                    request.setAttribute("ErrorMessage", "There's something wrong. Fail to perform task !!!");
                    setProductsList(request);
                    request.getRequestDispatcher(PRODUCTS_LIST).forward(request, response);
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action: " + action);
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
