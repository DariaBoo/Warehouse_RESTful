package ua.foxminded.warehouse.service.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents an item stored in the warehouse. Each item has a unique ID, code, name, description,
 * location, category, unit, selling price, and reorder quantity. An item can also have multiple
 * stock balances and transactions associated with it.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@ToString(exclude = {"stocks", "transactions"})
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "items", uniqueConstraints = {
        @UniqueConstraint(name = "name_price", columnNames = { "name", "selling_price" }) })
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 15)
    private String code;

    @NotBlank
    @Size(max = 15)
    private String name;

    @Size(max = 100)
    private String description;
    
    @NotBlank
    @Size(max = 10)
    private String location;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    @ManyToOne(targetEntity = Unit.class)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Positive
    @Column(name = "selling_price")
    private BigDecimal sellingPrice;
    
    @Positive
    @Column(name = "reorder_quantity")
    private BigDecimal reorderQuantity;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<StockBalance> stocks;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactions;
}
