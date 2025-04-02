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
import model.DTO.Account;

/**
 *
 * @author beu29
 */
public class AdminController extends HttpServlet {

    private final String ADMIN_HOME = "/view/private/admin/adminHome.jsp";
    private final String ADD_ACCOUNT = "/view/private/admin/AddAccount.jsp";
    private final String ACCOUNTS_LIST = "/view/private/admin/AccountsList.jsp";
    private final String UPDATE_ACCOUNT = "/view/private/admin/UpdateAccount.jsp";

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
    protected void setAccountsList(HttpServletRequest request) {
        List<Account> ls = new AccountDAO().listAll();
        request.setAttribute("accountsList", ls);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        String action = request.getParameter("action");
        if (action == null) {
            action = "home";
        }
        switch (action) {
            case "home":
                request.getRequestDispatcher(ADMIN_HOME).forward(request, response);
                break;
            case "addAccount":
                request.getRequestDispatcher(ADD_ACCOUNT).forward(request, response);
                break;
            case "accountsList":
                List<Account> ls = new AccountDAO().listAll();
                request.setAttribute("accountsList", ls);
                request.getRequestDispatcher(ACCOUNTS_LIST).forward(request, response);
                break;
            case "updateAccount":
                String account = request.getParameter("updateId");
                Account acc = new AccountDAO().getObjectById(account);
                request.setAttribute("acc", acc);
                request.getRequestDispatcher(UPDATE_ACCOUNT).forward(request, response);
                break;
            case "activateAccount":
                account = request.getParameter("activeId");
                acc = new AccountDAO().getObjectById(account);
                boolean kq = new AccountDAO().updateAccountStatus(acc);
                if (kq) {
                    setAccountsList(request);
                    request.getRequestDispatcher(ACCOUNTS_LIST).forward(request, response);
                } else {
                    request.setAttribute("ErrorMessage", "There's something wrong. Fail to perform task !!!");
                    setAccountsList(request);
                    request.getRequestDispatcher(ACCOUNTS_LIST).forward(request, response);
                }
                break;
            case "deleteAccount":
                account = request.getParameter("deleteId");
                acc = new AccountDAO().getObjectById(account);
                kq = new AccountDAO().deleteRec(acc);
                if (kq) {
                    setAccountsList(request);
                    request.getRequestDispatcher(ACCOUNTS_LIST).forward(request, response);
                } else {
                    request.setAttribute("ErrorMessage", "There's something wrong. Fail to perform task !!!");
                    setAccountsList(request);
                    request.getRequestDispatcher(ACCOUNTS_LIST).forward(request, response);
                }
                break;
            case "sortAccount":
                String accountId = request.getParameter("accountId");
                String fullName = request.getParameter("fullName");
                String gender = request.getParameter("gender");
                String phone = request.getParameter("phone");
                String isUse = request.getParameter("isUse");
                String roleIS = request.getParameter("roleInSystem");
                List<Account> sortAccList = new AccountDAO().sortAccount(accountId, fullName, gender, phone, isUse, roleIS);
                request.setAttribute("accountsList", sortAccList);
                request.getRequestDispatcher(ACCOUNTS_LIST).forward(request, response);
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
            case "addAccount":
                String account = request.getParameter("account").trim();
                String pass = request.getParameter("pass").trim();
                String lastName = request.getParameter("lastName");
                String firstName = request.getParameter("firstName");
                Date birthday = Date.valueOf(request.getParameter("birthday"));
                boolean gender = (request.getParameter("gender").equals("Male"));
                String phone = request.getParameter("phone");
                boolean isUse = "true".equals(request.getParameter("isUse"));
                int roleInSystem = Integer.parseInt(request.getParameter("roleInSystem"));

                Account acc = new Account(account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem);
                boolean kq = new AccountDAO().insertRec(acc);

                if (kq) {
                    setAccountsList(request);
                    request.getRequestDispatcher(ACCOUNTS_LIST).forward(request, response);
                } else {
                    request.setAttribute("ErrorMessage", "There's something wrong. Fail to perform task !!!");
                    request.getRequestDispatcher(ACCOUNTS_LIST).forward(request, response);
                }
                break;
            case "updateAccount":
                account = request.getParameter("account");
                pass = request.getParameter("pass");
                lastName = request.getParameter("lastName");
                firstName = request.getParameter("firstName");
                birthday = Date.valueOf(request.getParameter("birthday"));
                gender = (request.getParameter("gender").equals("Male"));
                phone = request.getParameter("phone");
                isUse = "true".equals(request.getParameter("isUse"));
                roleInSystem = Integer.parseInt(request.getParameter("roleInSystem"));

                acc = new Account(account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem);
                kq = new AccountDAO().updateRec(acc);

                if (kq) {
                    setAccountsList(request);
                    request.getRequestDispatcher(ACCOUNTS_LIST).forward(request, response);
                } else {
                    request.setAttribute("ErrorMessage", "There's something wrong. Fail to perform task !!!");
                    request.getRequestDispatcher(ACCOUNTS_LIST).forward(request, response);
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
