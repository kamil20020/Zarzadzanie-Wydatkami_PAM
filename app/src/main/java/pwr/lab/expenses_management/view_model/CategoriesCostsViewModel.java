package pwr.lab.expenses_management.view_model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import pwr.lab.expenses_management.data.dao.ExpenseDAO;

public interface CategoriesCostsViewModel {

    @Data
    @AllArgsConstructor
    class MonthlyReport {
        private List<ExpenseDAO.CategoryCost> categoriesCosts = new ArrayList<>();
        private double totalCost = 0;
    }

    ExpenseDAO.CategoryCost get(int index);
    double getTotalCost();
    int size();
}
