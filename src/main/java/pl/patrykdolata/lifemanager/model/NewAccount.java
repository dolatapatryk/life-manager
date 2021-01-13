package pl.patrykdolata.lifemanager.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class NewAccount {
    String name;
    BigDecimal startBalance;
}
