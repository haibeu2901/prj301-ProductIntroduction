/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.util.List;

/**
 *
 * @author beu29
 */
public interface Accessible<T> {
    
    boolean insertRec(T o);
    boolean updateRec(T o);
    boolean deleteRec(T o);
    T getObjectById(String id);
    List<T> listAll();
    
}
