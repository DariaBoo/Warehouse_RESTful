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
public class ItemReport {
    
    @Id
    private int id;

    private long itemId;

    private BigDecimal incomings;
    
    private BigDecimal outgoings;
    
    private BigDecimal openBalance;
    
    private BigDecimal closeBalance;
}
