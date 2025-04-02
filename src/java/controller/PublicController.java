/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.DAO.CategoryDAO;
import model.DAO.ProductDAO;
import model.DTO.Category;
import model.DTO.Product;

/**
 *
 * @author beu29
 */
public class PublicController extends HttpServlet {

    private final String HOME = "/view/public/index.jsp";
    private final String PRODUCT_LIST = "/view/public/ProductList.jsp";
    private final String PRODUCT_DETAIL = "/view/public/ProductDetail.jsp";

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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        List<Category> catLs = new CategoryDAO().listAll();
        request.setAttribute("categoriesList", catLs);

        // Get recent products from cookies
        List recentProducts = (List) request.getAttribute("recentProducts");
        if (recentProducts == null || recentProducts.isEmpty()) {
            recentProducts = getRecentProductsFromCookies(request);
            if (recentProducts != null && !recentProducts.isEmpty()) {
                request.setAttribute("recentProducts", recentProducts);
            }
        }

        List<Product> suggestProducts = getSuggestProductsBasedPrice(request);
        request.setAttribute("suggestProducts", suggestProducts);

        String action = request.getParameter("action");

        if (action == null) {
            action = "home";
        }

        switch (action) {
            case "home":
                request.getRequestDispatcher(HOME).forward(request, response);
                break;
            case "productList":
                List<Product> ls = new ProductDAO().listAll();
                request.setAttribute("productsList", ls);
                request.getRequestDispatcher(PRODUCT_LIST).forward(request, response);
                break;
            case "search":
                String search = request.getParameter("searchBar");
                List<Product> searchList = new ProductDAO().searchProducts(search);
                request.setAttribute("productsList", searchList);
                request.getRequestDispatcher(PRODUCT_LIST).forward(request, response);
                break;
            case "sort":
                String priceRange = request.getParameter("priceRange");
                String min = request.getParameter("minPrice");
                int minPrice = (min != null && !min.isEmpty()) ? Integer.parseInt(min) : Integer.MIN_VALUE;
                String max = request.getParameter("maxPrice");
                int maxPrice = (max != null && !max.isEmpty()) ? Integer.parseInt(max) : Integer.MAX_VALUE;
                String productName = request.getParameter("productName");
                String hasDiscount = request.getParameter("hasDiscount");
                String cat = request.getParameter("category");
                int catId = (cat != null && !cat.isEmpty()) ? Integer.parseInt(cat) : 0;

                List<Product> sortList = new ProductDAO().sortProduct(priceRange, minPrice, maxPrice, productName, hasDiscount, catId);

                request.setAttribute("productsList", sortList);
                request.getRequestDispatcher(PRODUCT_LIST).forward(request, response);
                break;
            case "productDetail":
                String productId = request.getParameter("productId");
                Product pro = new ProductDAO().getObjectById(productId);
                request.setAttribute("p", pro);
                addProductToCookies(productId, response);
                request.setAttribute("recentProducts", getRecentProductsFromCookies(request));
                request.getRequestDispatcher(PRODUCT_DETAIL).forward(request, response);
                break;
            default:
                System.out.println("Unknown action: " + action);
                request.getRequestDispatcher(HOME).forward(request, response); // Fallback to home
                break;
        }
    }

    // Simple method to add product ID to cookie
    private void addProductToCookies(String productId, HttpServletResponse response) {
        String cookieName = "recent_" + System.currentTimeMillis();
        Cookie cookie = new Cookie(cookieName, productId);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    // Method to get recent products from cookies using HashSet to eliminate duplicates
    private List getRecentProductsFromCookies(HttpServletRequest request) {
        ServletContext sc = getServletContext();
        List<Product> ls = (List<Product>) request.getAttribute("recentProducts");

        if (ls == null) {
            ls = new ArrayList<>();
            Set<String> uniqueProductIds = new HashSet<>();
            Cookie[] cookies = request.getCookies();
            int maxRecent = 6;
            int count = 0;

            if (cookies != null) {
                for (int i = cookies.length - 1; i >= 0 && count < maxRecent; i--) {
                    Cookie cookie = cookies[i];
                    if (cookie.getName().startsWith("recent_")) {
                        String productId = cookie.getValue();
                        if (uniqueProductIds.add(productId)) {
                            Product pro = new ProductDAO().getObjectById(productId);
                            if (pro != null) {
                                ls.add(pro);
                                count++;
                            }
                        }
                    }
                }
            }
        }
        sc.setAttribute("recentProducts", ls);
        return ls;
    }

    private List<Product> getSuggestProductsBasedPrice(HttpServletRequest request) {
        List<Product> recentProducts = getRecentProductsFromCookies(request);
        if (recentProducts == null || recentProducts.isEmpty()) {
            recentProducts = new ArrayList<>();
        }
//        count price range based on product price
        int lowPrice = 0, midPrice = 0, highPrice = 0;
        for (Product p : recentProducts) {
            if (p != null) {
                int price = p.getPrice();
                if (price < 5000000) {
                    lowPrice++;
                } else if (5000000 <= price && price <= 15000000) {
                    midPrice++;
                } else {
                    highPrice++;
                }
            }
        }

//        determine dominent price range
        String dominentRange = "";
        if (lowPrice >= midPrice && lowPrice >= highPrice) {
//            lowPrice is dominent
            dominentRange = "low";
        } else if (midPrice >= lowPrice && midPrice >= highPrice) {
//            midPrice is dominent
            dominentRange = "mid";
        } else {
//            highPrice is dominent
            dominentRange = "high";
        }

//        suggest products
        int minPrice = Integer.MIN_VALUE, maxPrice = Integer.MAX_VALUE;
        switch (dominentRange) {
            case "low":
                minPrice = 0;
                maxPrice = 5000000;
                break;
            case "mid":
                minPrice = 5000000;
                maxPrice = 15000000;
                break;
            case "high":
                minPrice = 15000000;
                maxPrice = Integer.MAX_VALUE;
        }
        List<Product> allProducts = new ProductDAO().listAll();
        List<Product> suggestProducts = new ArrayList<>();
        Set<Product> uniqueProducts = new HashSet<>();
        int maxSuggest = 6, suggestCount = 0;
        for (Product p : allProducts) {
            if (p != null && suggestCount < maxSuggest) {
                if (p.getPrice() >= minPrice && p.getPrice() <= maxPrice && uniqueProducts.add(p)) {
                    suggestProducts.add(p);
                    suggestCount++;
                }
            }
        }
        return suggestProducts;
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
        processRequest(request, response);
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
