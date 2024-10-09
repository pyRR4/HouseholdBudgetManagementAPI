package com.example.transactions;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.text.DateFormat;
import java.util.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;
    private double transaction_value;
    private Date transaction_date;
    private boolean is_expense;
    private String transaction_category;

    private static ArrayList<String> categories = new ArrayList<>(List.of("DEFAULT"));

    public Transaction() {}

    public Transaction(double transaction_value, Date transaction_date, boolean is_expense, String transaction_category) {
        this.transaction_value = transaction_value;
        this.transaction_date = transaction_date;
        this.is_expense = is_expense;
        String upperCaseCategory = transaction_category.toUpperCase();
        if(categories.contains(upperCaseCategory)) {
            this.transaction_category = upperCaseCategory;
        } else {
            this.transaction_category = "DEFAULT";
        }
    }

    public void setTransaction_id(Long id) {
        this.transaction_id = id;
    }

    public Long getTransaction_id() {
        return transaction_id;
    }

    public double getTransaction_value() {
        return transaction_value;
    }

    public void setTransaction_value(double value) {
        this.transaction_value = value;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Date date) {
        this.transaction_date = date;
    }

    public boolean isIs_expense() {
        return is_expense;
    }

    public void setIs_expense(boolean is_expense) {
        this.is_expense = is_expense;
    }

    public String getTransaction_category() {
        return transaction_category;
    }

    public void setTransaction_category(String category) {
        this.transaction_category = category;
    }

    public static ArrayList<String> getCategories() {
        return categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Objects.equals(transaction_id, that.transaction_id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(transaction_id);
    }

    @Override
    public String toString() {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
        return "Transaction{" +
                "id=" + transaction_id +
                ", value=" + transaction_value +
                ", date=" + df.format(transaction_date) +
                ", isExpense=" + is_expense +
                ", category='" + transaction_category + '\'' +
                '}';
    }

    public static void addCategory(String category) {
        if(!categories.contains(category)) {
            categories.add(category.toUpperCase());
        } else {
            //??
        }
    }

    public static void removeCategory(String category) {
        if(categories.contains(category)) {
            categories.remove(category.toUpperCase());
        } else {
            //??
        }
    }
}

