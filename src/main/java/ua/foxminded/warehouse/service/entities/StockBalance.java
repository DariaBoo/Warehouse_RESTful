package ua.foxminded.warehouse.service.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents the current balance of a particular item in the warehouse.
 * This class tracks the remaining stock of an item on a particular date.
 * @author Bohush Darya
 * @version 1.0
 *
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "stock_Balance")
public class StockBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    
    @PositiveOrZero
    @Column(name = "stock_remainder")
    private BigDecimal stockRemainder;
}
