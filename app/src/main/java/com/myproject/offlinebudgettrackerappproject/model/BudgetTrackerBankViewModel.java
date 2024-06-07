package com.myproject.offlinebudgettrackerappproject.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.myproject.offlinebudgettrackerappproject.data.BudgetTrackerBankRepository;

import java.util.ArrayList;
import java.util.List;

public class BudgetTrackerBankViewModel extends AndroidViewModel {

    public static BudgetTrackerBankRepository bankRepository;
    private List<String> bankNames;
    private List<BudgetTrackerBank> bankList;
    private ArrayList<BudgetTrackerBank> bankArrayList;
    private List<BudgetTrackerBank> bankNameLists;

    public BudgetTrackerBankViewModel(@NonNull Application application) {
        super(application);
        bankRepository = new BudgetTrackerBankRepository(application);
        bankNames = bankRepository.getBankRepositoryBankNames();
        bankList = bankRepository.getBankRepositoryBankList();
    }


    public List<String> getBankViewModelBankNames() {

        return bankNames;
    }

    public List<BudgetTrackerBank> getBankViewModelBankList() {
        bankRepository.getBankRepositoryBankList();
        return bankList;
    }

    public static void insert(BudgetTrackerBank budgetTrackerBank) {
        bankRepository.insert(budgetTrackerBank);
    }

    public List<BudgetTrackerBank> getBankNameLists(String bankName) {
        bankNameLists = bankRepository.queryBankName(bankName);
        return bankNameLists;
    }

    public LiveData<BudgetTrackerBank> getBudgetTrackerBankId(int id) {return bankRepository.getBudgetTrackerBankId(id);}

    public void updateAddition(int incomeNum, String bankName) {
        bankRepository.updateAddition(incomeNum, bankName);
    }

    public void updateSubtraction(int spendingNum, String bankName) {
        bankRepository.updateSubtraction(spendingNum, bankName);
    }

    public static void updateBudgetTrackerBank(BudgetTrackerBank budgetTrackerBank) {bankRepository.updateBudgetTrackerBank(budgetTrackerBank);}

    public static void deleteBudgetTrackerBank(BudgetTrackerBank budgetTrackerBank) {bankRepository.deleteBudgetTrackerBank(budgetTrackerBank);}


}
