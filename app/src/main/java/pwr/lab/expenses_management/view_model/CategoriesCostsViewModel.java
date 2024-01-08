package pwr.lab.expenses_management.view_model;

import pwr.lab.expenses_management.data.dao.ExpenseDAO;

public interface CategoriesCostsViewModel {

    ExpenseDAO.CategoryCost get(int index);
    double getTotalCost();
    int size();
}
