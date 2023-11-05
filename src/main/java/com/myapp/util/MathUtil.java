package com.myapp.util;
// Replace with your actual import
import java.util.List;

import ProjetJee.ProjetJee.Entity.DetailCommande;

public class MathUtil {

    public static double calculateTotal(List<DetailCommande> detailCommandeList) {
        return detailCommandeList.stream()
                .filter(dc -> dc.getCategoriePlace().getDetailProduit().getProduit().getName() != null && !dc.getCategoriePlace().getDetailProduit().getProduit().getName().isEmpty())
                .mapToDouble(dc -> dc.getCategoriePlace().getPrix() * dc.getQuantite())
                .sum();
    }
}