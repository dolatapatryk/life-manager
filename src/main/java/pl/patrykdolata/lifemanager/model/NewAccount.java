package pl.patrykdolata.lifemanager.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewAccount {
    String name;
    BigDecimal startBalance;
}
