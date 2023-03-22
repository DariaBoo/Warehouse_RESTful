package ua.foxminded.warehouse.service.dto;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Report {

    @Id
    private long item_id;
    
    private BigDecimal item_price;

    private BigDecimal transactions_quantity;

    private BigDecimal transactions_total_price;
    
    private BigDecimal open_balance;
    
    private BigDecimal close_balance;
}
